package com.ruoyi.project.module.workload.controller;

import com.ruoyi.common.utils.file.FTPUtils;
import com.ruoyi.common.utils.file.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.module.workload.domain.WorkloadRequestBean;
import com.ruoyi.project.module.workload.domain.WorkloadResponseBean;
import com.ruoyi.project.module.workload.mapper.WkWorkloadMapper;
import com.ruoyi.project.module.workload.service.IWkWorkloadService;
import com.ruoyi.project.system.role.domain.Role;
import com.ruoyi.project.system.user.domain.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工作量Controller
 * @author cj
 * @date 2020-11-13
 */
@Controller
@RequestMapping("/workload")
public class WkWorkloadController extends BaseController
{
    @Autowired
    private IWkWorkloadService wkWorkloadService;

    @Autowired
    private WkWorkloadMapper wkWorkloadMapper;

    @GetMapping("/jobs")
    public String jobs()
    {
        return "workload/jobs";
    }

    @GetMapping("/accountingJobs")
    public String accountingJobs()
    {return "workload/accountingJobs"; }

    /**
     * 查询用户角色信息
     * @return
     */
    @PostMapping("/getRole")
    @ResponseBody
    @ApiOperation(value = "查询用户角色信息", notes = "工作量模块-查询用户角色")
    public AjaxResult getRole()
    {
        Map<String,String> reqMap = new HashMap<String,String>();
        User user = getSysUser();
        reqMap.put("userId",String.valueOf(user.getUserId()));
        reqMap.put("isAll","");
        List<Role> list = user.getRoles();
        for (Role role:list) {
            if("主管".equals(role.getRoleName())){
                reqMap.put("isAll","isAll");
            }else{
                reqMap.put("roleId",String.valueOf(role.getRoleId()));
            }
        }
        return AjaxResult.success("查询成功", reqMap);
    }

    /**
     * 查询工作量清单列表
     * @param bean
     * @return
     */
    @PostMapping("/findWorkload")
    @ResponseBody
    @ApiOperation(value = "查询工作量列表",notes = "")
    public TableDataInfo findWorkload(WorkloadRequestBean bean){
        startPage();
        List<WorkloadResponseBean> list = wkWorkloadService.findWorkload(bean);
        return getDataTable(list);
    }


    /**
     * 根据需求id查询 基本信息
     * @param demandId
     * @return
     */
    @PostMapping("/getDemandById")
    @ResponseBody
    @ApiOperation(value = "查询工作量基本信息",notes = "")
    public AjaxResult getDemandById(@RequestParam("demandId")String demandId){
        WorkloadResponseBean bean = wkWorkloadService.queryDemandById(demandId);
        if(bean == null){
            return error("查询失败");
        }
        return AjaxResult.success("查询成功",bean);
    }

    /**
     * 根据工作量id查询工作量
     * @param workloadId
     * @return
     */
    @PostMapping("/getWorkloadById")
    @ResponseBody
    @ApiOperation(value = "查询工作量",notes = "")
    public AjaxResult getWorkloadById(@RequestParam("workloadId")String workloadId){
        WorkloadResponseBean bean = wkWorkloadService.getWorkloadById(workloadId);
        if(bean == null){
            return error("查询失败");
        }
        return AjaxResult.success("查询成功",bean);
    }


    /**
     * 根据工作量id核算工作量
     * @param workTimeType
     * @return
     */
    @PostMapping("/checkWorkloadById")
    @ResponseBody
    @ApiOperation(value = "核算/修改工作量",notes = "核算工作量")
    public AjaxResult checkWorkloadById(@RequestParam(value = "difficultDegree",required = false )String difficultDegree,
                                        @RequestParam(value = "workNum",required = false )String workNum,
                                        @RequestParam(value = "workTimeType",required = false )String workTimeType,
                                        @RequestParam(value = "workTime" ,required = false )String workTime,
                                        @RequestParam(value = "workContent",required = false )String workContent,
                                        @RequestParam(value = "workText",required = false )String workText,
                                        @RequestParam(value = "workloadId",required = false )String workloadId,
                                        @RequestParam(value = "normalHours",required = false )String normalHours,
                                        @RequestParam(value = "outputName",required = false )String fileName,
                                        @RequestParam(value = "outputUrl",required = false )String fileUrl,
                                        @RequestParam(value = "hasOutput",required = false )String hasOutPut
    ){
        //判断前端参数
        if(StringUtils.isFullNull(difficultDegree)){
            return error("难度系数为必填项");
        }
        //判断前端参数
        if(StringUtils.isFullNull(workContent)){
            return error("工作内容为必填项");
        }

        if(StringUtils.isFullNull(workTimeType)){
            return error("工时类型为空");
        }else {
            if("0".equals(workTimeType) && StringUtils.isFullNull(workTime)){
                return error("工时为空");
            }
            if("1".equals(workTimeType) && StringUtils.isFullNull(normalHours)){
                return error("标准工时为空");
            }
        }

        if(StringUtils.isFullNull(workNum)){
            return error("数量为必填项");
        }

        if(null != workContent && workContent.length() > 500){
            return error("工作内容过长");
        }
        if("0".equals(workTimeType)){
            //查询当月工作日，公式 ：工时/8/当月工作日 =人月
            SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
            Map<String,String> map = wkWorkloadMapper.getDay(format.format(new Date()));
            String days = map.get("WORKDAY");
            //计算人月
            double wkTime = Double.valueOf(workTime);//工时
            double coefficient = Double.valueOf(difficultDegree);//系数
            double day = Double.valueOf(days);//当月工作日
            double manDay = wkTime / 8 / day;//得出人月
            int rest = wkWorkloadService.updateWorkloadById(difficultDegree,workNum,workTime,String.valueOf(manDay),workContent,workText,workloadId,fileName,fileUrl,hasOutPut );
            return rest > 0 ?AjaxResult.success("成功"):AjaxResult.error("失败");
        }else if("1".equals(workTimeType)){
            // 数量/参考工时*难度系数 =人月
            SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
            double num = Double.valueOf(workNum);// 数量
            double normal = Double.valueOf(normalHours);// 标准工时
            double coefficient = Double.valueOf(difficultDegree);// 难度系数
            double manDay = num/normal*coefficient;//  人/月
            workTime = "";// 工时滞空
            int rest = wkWorkloadService.updateWorkloadById(difficultDegree,workNum,workTime,String.valueOf(manDay),workContent,workText,workloadId,fileName,fileUrl,hasOutPut );
            return rest > 0 ?AjaxResult.success("成功"):AjaxResult.error("失败");
        }
        return error("错误的核算类型");
    }

        /**
         * 查询笔记
         * @param demandId
         * @param bigTypeId
         * @param smallTypeId
         * @param moduelId
         * @return
         */
        @PostMapping("/findNote")
        @ResponseBody
        @ApiOperation(value = "查询笔记",notes = "查询笔记")
        public AjaxResult findNote(@RequestParam("demandId")String demandId,
                @RequestParam("bigTypeId")String bigTypeId,
                @RequestParam("smallTypeId")String smallTypeId,
                @RequestParam("moduleId")String moduelId,
                @RequestParam("workDate")String workDate
    ){
        if(StringUtils.isFullNull(demandId)){
            return error("需求id参数为空");
        }
        if(StringUtils.isFullNull(bigTypeId)){
            return error("工作大类id参数为空");
        }
        if(StringUtils.isFullNull(smallTypeId)){
            return error("工作小类id参数为空");
        }
        if(StringUtils.isFullNull(moduelId)){
            return error("工作量模型id参数为空");
        }
        //根据当前月份/需求id/工作大类/工作小类/工作项
        if(StringUtils.isFullNull(workDate)){
            SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
            workDate = format.format(new Date());
        }
        List<WorkloadResponseBean> list = wkWorkloadService.findNote(demandId,bigTypeId,smallTypeId,workDate.replaceAll("-",""),moduelId);
        if(null == list || 1 > list.size()){
            return AjaxResult.success("无笔记",null);
        }
        List<Object> reList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        String str = "";//工作时长初始值
        String hh = "";//时
        String mm = "";//分
        int rehh = 0;//计算的小时
        int remm = 0;//计算的分
        for (WorkloadResponseBean bean : list) {
            str = bean.getWorkHours();
            hh = str.substring(0, str.indexOf(":"));
            mm = str.substring(str.indexOf(":") + 1, str.length());
            rehh = rehh + Integer.valueOf(hh);
            remm = remm + Integer.valueOf(mm);
        }
        hh = String.valueOf(rehh + remm / 60);
        if (remm == 0) {
            mm = "00";
        } else {
            mm = String.valueOf(remm % 60);
        }
        map.put("workingHours", hh + ":" + mm);
        map.put("workCount",String.valueOf(list.size()));//累计工作次数
        reList.add(map);
        reList.add(list);
        return AjaxResult.success("查询成功",reList);
    }


    /**
     * 根据角色查询大类id下现有的所属小类
     * @param demandId
     * @param bigTypeId
     * @return
     */
    @PostMapping("/findSmallType")
    @ResponseBody
    @ApiOperation(value = "查询小类", notes = "")
    public AjaxResult findSmallType(@RequestParam("demandId") String demandId,@RequestParam("bigTypeId")String bigTypeId,@RequestParam("workDate")String workDate ){
        User user = ShiroUtils.getSysUser();
        List<Role> roles = user.getRoles();
        String roleId = "";
        for (Role role:roles) {
            roleId = String.valueOf(role.getRoleId());
            if(role.getRoleName().equals("主管")){
                roleId = "主管";
                break;
            }
        }
        List<WorkloadResponseBean> list = wkWorkloadService.findSmallType(bigTypeId, roleId, demandId, workDate.replaceAll("-",""));
        if(null == list || list.size() < 1){
            return AjaxResult.success("当前工作大类下无工作小类数据支撑",null);
        }
        return AjaxResult.success("查询成功",list);
    }


    /**
     * 根据角色查询小类下现有的工作项
     * @param demandId
     * @param smallTypeId
     * @return
     */
    @PostMapping("/findWorkItem")
    @ResponseBody
    @ApiOperation(value = "查询工作项", notes = " ")
    public AjaxResult findWorkItem(@RequestParam("demandId") String demandId,@RequestParam("smallTypeId")String smallTypeId,@RequestParam("workDate")String workDate ){
        User user = ShiroUtils.getSysUser();
        List<Role> roles = user.getRoles();
        String roleId = "";
        for (Role role:roles) {
            roleId = String.valueOf(role.getRoleId());
            if(role.getRoleName().equals("主管")){
                roleId = "主管";
                break;
            }
        }
        List<WorkloadResponseBean> list = wkWorkloadService.findWorkItem(smallTypeId, roleId, demandId, workDate.replaceAll("-",""));
        if(null == list || list.size() < 1){
            return AjaxResult.success("当前工作小类下无工作项数据支撑",null);
        }
        return AjaxResult.success("查询成功",list);
    }


    /**
     * 导出
     * @param bean
     * @return
     */
    @PostMapping("/exlWorkload")
    @ResponseBody
    @ApiOperation(value = "导出工作量清单", notes = " ")
    public AjaxResult exlWorkload(WorkloadRequestBean bean){
        List<WorkloadResponseBean> list = wkWorkloadService.exlWorkload(bean);
        if( null != list){
            ExcelUtil<WorkloadResponseBean> util = new ExcelUtil<>(WorkloadResponseBean.class);
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            String fileName = format.format(new Date())+"-和生活项目工作量清单";
            return util.exportExcel(list,fileName);
        }
        return AjaxResult.error("无数据导出，请重试");
    }


//    @GetMapping("/exlWorkload")
//    @ResponseBody
//    @ApiOperation(value = "导出工作量清单", notes = " ")
//    public AjaxResult exlWorkload(WorkloadRequestBean bean, HttpServletResponse response){
//        List<WorkloadResponseBean> list = wkWorkloadService.findWorkload(bean);
//        if( null != list){
//            ExcelUtil<WorkloadResponseBean> util = new ExcelUtil<>(WorkloadResponseBean.class);
//            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//            String fileName = format.format(new Date())+"-和生活项目工作量清单";
//            util.exportExcel(list,fileName);
//            try {
//                FTPUtils.download(fileName+".xlsx", fileName+".xlsx", response);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return success();
//        }
//        return AjaxResult.error("无数据导出，请重试");
//    }


    @PostMapping("/uploadFiles")
    @ResponseBody
    @ApiOperation(value = "多文件上传", notes = "")
    public AjaxResult uploadFiles(@RequestParam("files")MultipartFile[] files,
                                  @RequestParam("demandId")String demandId,
                                  @RequestParam("moduleId")String moduleId,
                                  @RequestParam("workloadId")String workloadId
    ){
        Map<String,String> fileMap = wkWorkloadService.getFileName(workloadId);
        String path = wkWorkloadService.filePath(fileMap.get("WORKDATE"),demandId, moduleId);
        boolean boo = FTPUtils.upload(files,path);
        if(boo) {
            Map<String,String> map = new HashMap<>();
            StringBuilder builder = new StringBuilder();
            String fileName = fileMap.get("FILENAME");
            for (int i = 0; i <files.length ; i++) {
                String newfileName = files[i].getOriginalFilename();
                if(!StringUtils.isFullNull(fileName)){
                    if(!fileName.contains(newfileName)){
                        builder.append(",").append(newfileName);
                    }
                }else{
                    builder.append(",").append(newfileName);
                }
            }
            if(StringUtils.isFullNull(fileName)){
                map.put("outputName",builder.toString().substring(1));
            }else {
                map.put("outputName",fileName+builder.toString());
            }
            map.put("outputUrl",path);
            map.put("hasOutput","1");
            return AjaxResult.success("上传成功",map);
        }
        return error("上传失败");
    }

    @PostMapping("/deleteFile")
    @ResponseBody
    @ApiOperation(value = "删除文件", notes = "")
    public AjaxResult deleteFile(@RequestParam("fileUrl") String fileUrl,
                                 @RequestParam("fileName") String fileName,
                                 @RequestParam("newFileName") String newFileName,
                                 @RequestParam("workloadId") String workloadId) {
        boolean boo = FTPUtils.delete(fileName, fileUrl);
        if(!boo) return error("删除失败");
        String hasOutput = "1";// 输出物默认为1 有输出物
        if(StringUtils.isFullNull(newFileName)){
            hasOutput = "0";
        }
        int num = wkWorkloadService.updateFileName(newFileName, workloadId,hasOutput);
        if (num < 1) return error("保存数据库失败");
        return success("删除成功");
    }

    @GetMapping("/querySmallTypes")
    @ResponseBody
    @ApiOperation(value = "查询所有小类", notes = "")
    public AjaxResult querySmallTypes(){
        List<WorkloadResponseBean> list = wkWorkloadService.querySmallTypes();
        if(null == list || list.size() < 1){
            return error("查询失败");
        }
        return AjaxResult.success("查询成功",list);
    }
}