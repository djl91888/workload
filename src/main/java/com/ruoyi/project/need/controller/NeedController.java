package com.ruoyi.project.need.controller;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.need.domain.Export;
import com.ruoyi.project.need.domain.Need;
import com.ruoyi.project.need.service.NeedService;
import com.ruoyi.project.need.utils.util;
import com.ruoyi.project.system.role.domain.Role;
import com.ruoyi.project.system.user.domain.User;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 需求操作
 * 
 * @author cty
 */
@Controller
@RequestMapping("/need")
public class NeedController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(NeedController.class);



    private String prefix = "need";
    @Autowired
    NeedService needService;
    @GetMapping("/permission")
    @ApiOperation(value = "获取角色", notes = "工作量管理-获取角色(超级管理员和主管返回状态为1，产品和运营返回2,其他角色返回0)")
    @ResponseBody
    public int  list()
    {
        //查询当前用户的id
        User user = getSysUser();
        Long userId = user.getUserId();//拿到用户id
        Map map = new HashMap();
        if(!(userId == null || userId.longValue() == 0)){
            //不为空去关联表里获取所有的角色
            List<Role>  roleList=needService.selectRole(userId);
            for(int i=0;i<roleList.size();i++){
                String roleName = roleList.get(i).getRoleName();
                map.put("num"+i,roleName);
            }


        }
        if(map.containsValue("主管") ){
            return 1;

        }else if(map.containsValue("产品") || map.containsValue("运营")){
            return 2;
        }
        else{
            return 0;
        }

    }
    /**
     * 跳转新建需求页面
     */
    @GetMapping("/newCreatePage")
    @ApiOperation(value = "跳转新建需求页面", notes = "工作量管理-跳转新建需求页面")
    public String  newCreatePage()
    {

        return "workload/newDemand";

    }
    /**
     * 我的需求查询
     * @return
     */
    @PostMapping("/NeedMyList")
    @ApiOperation(value = "我的需求查询", notes = "工作量管理-我的需求查询")
    @ResponseBody
    public List<Need> NeedMyList(@RequestParam(name="demandContent", defaultValue="") String demandContent,
                                 @RequestParam(name="cqNum", defaultValue="") String cqNum,
                                 @RequestParam(name="createStartTime", defaultValue="") String createStartTime,
                                 @RequestParam(name="createEndTime", defaultValue="") String createEndTime,
                                 @RequestParam(name="demandTypeId", defaultValue="") String demandTypeId,
                                 @RequestParam(name="demandStatus", defaultValue="") String demandStatus,
                                 @RequestParam(name="createrId", defaultValue="") String createrId,
                                 @RequestParam(name="sourceBy", defaultValue="") String sourceBy,
                                 @RequestParam(name="relevant", defaultValue="") String relevant,
                                 @RequestParam(name="people", defaultValue="") String people
    ) {
        Need need=new Need();
        need.setDemandContent(demandContent);
        need.setCqNum(cqNum);
        need.setCreateStartTime(createStartTime);
        need.setCreateEndTime(createEndTime);
        need.setDemandTypeId(demandTypeId);
        need.setDemandStatus(demandStatus);
        need.setCreaterId(createrId);

        need.setSourceBy(sourceBy);
        need.setPeople(people);
        need.setRelevant(relevant);
        List<Need> collectList=null;
        int needUserId = list();
        try {
            User user = getSysUser();
            Long userId = user.getUserId();//拿到用户id
            String usid = String.valueOf(userId);
            need.setRelevant(usid);
            need.setPeople(usid);
            if(usid.equals(createrId) || StringUtils.isEmpty(createrId)){
                need.setCreaterId(usid);
            }else{
                need.setCreaterId(createrId);
            }
            if(!StringUtils.isEmpty(relevant)){
                need.setRelevant1(relevant);
            }
            if(!StringUtils.isEmpty(people)){
                need.setPeople1(people);
            }
            collectList=needService.NeedMycreater(need);

            for (Need n:collectList) {
                if(needUserId==1){
                    n.setEditStatus("1");//是主管
                }else{
                    String creater = n.getCreaterId();//创建人
                    if(creater.equals(usid)){
                        n.setEditStatus("1");//是创建人
                    }

                    String person=n.getPerSon();//需求负责人
                    if(!StringUtils.isEmpty(person)){
                        String[] split = person.split(",");
                        for (String userid : split) {
                            if(userid.equals(usid)){
                                if(needUserId==2){
                                    //是产品或者运营
                                    n.setEditStatus("1");//是创建人
                                }

                            }
                        }
                    }




                }




            }

//            //先查询自己是创建者的所有需求
//            User user = getSysUser();
//            Long userId = user.getUserId();//拿到用户id
//
//            String usid = String.valueOf(userId);
//            need.setUserId(usid);
//            if(usid.equals(createrId) || StringUtils.isEmpty(createrId)){
//                //创建者自己
//                needMycreater=needService.NeedMycreater(need);
//                for(Need n:needMycreater){
//                    //这个列表的数据都可编辑
//                    n.setEditStatus("1");
//                }
//            }else{
//                //不是自己创建的需求
//
//                    needMycreater=needService.Needothercreater(need);
//                    for(Need n:needMycreater){
//                        //这个列表的数据都不可编辑
//                        n.setEditStatus("0");
//                    }
//
//
//
//            }
//            if(!StringUtils.isEmpty(people)){
//                need.setUserId(people);
//                if(!StringUtils.isEmpty(createrId)){
//                    need.setCreaterId(createrId);
//                }else{
//                    need.setCreaterId(usid);
//                }
//
//            }
//
//            //在查自己是否是需求负责人的需求
//            NeedMyList= needService.NeedMyList(need);
//            Map map = new HashMap();
//            for(Need n:NeedMyList){
//                //判断需求负责人是不是主管或者超级管理员
//
//                if(needUserId==1){
//                    n.setEditStatus("1");
//                }else{
//                    n.setEditStatus("0");
//                }
//
//            }
//            if(!StringUtils.isEmpty(relevant)){
//                need.setUserId(relevant);
//                if(!StringUtils.isEmpty(createrId)){
//                    need.setCreaterId(createrId);
//                }else{
//                    need.setCreaterId(usid);
//                }
//
//            }
//
//            //然后查询相关人员的需求
//            NeedContactList=needService.NeedContactList(need);
//            for(Need n:NeedContactList){
//
//
//                    //判断需求负责人是不是主管或者超级管理员
//                    if(needUserId==1){
//                        n.setEditStatus("1");
//                    }else{
//                        n.setEditStatus("0");
//                    }
//
//
//            }
//
//                collectList = Stream.of(needMycreater, NeedMyList,NeedContactList)
//                        .flatMap(Collection::stream)
//                        .distinct()
//                        .sorted(Comparator.comparing(Need::getCreateTime).reversed())
//                        .collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
        }
        return collectList;

    }
    /**
     * 跳转新建需求页面
     */
    @PostMapping("/deleteDemand")
    @ApiOperation(value = "删除需求", notes = "工作量管理-删除需求")
    @ResponseBody
    public AjaxResult deleteDemand(@RequestParam(name="demandId", defaultValue="") String demandId)
    {
        int list = list();
        if(list==1){
            try{
                if(StringUtils.isEmpty(demandId)){
                    return error("删除失败,获取不到需求id");
                }
                int i=needService.selectBj(demandId);
                if(i>0){
                    return error("删除失败,该需求已经存在笔记");
                }else{
                    int j= needService.deleteBj(demandId);
                    if(j>0){
                        return success("删除成功!");
                    }else{
                        return error("删除失败！");
                    }

                }

            }catch (Exception r){
                error("删除失败！");
                r.printStackTrace();
            }
            return error("删除失败！");

        }else{
            return error("删除失败！不是主管");
        }


    }
    /**
     * 全部的需求查询
     * @return
     */
    @PostMapping("/NeedLists")
    @ApiOperation(value = "全部的需求查询", notes = "工作量管理-全部的需求查询")
    @ResponseBody
    public List<Need> NeedLists( @RequestParam(name="demandContent", defaultValue="") String demandContent,
                                 @RequestParam(name="cqNum", defaultValue="") String cqNum,
                                 @RequestParam(name="createStartTime", defaultValue="") String createStartTime,
                                 @RequestParam(name="createEndTime", defaultValue="") String createEndTime,
                                 @RequestParam(name="demandTypeId", defaultValue="") String demandTypeId,
                                 @RequestParam(name="demandStatus", defaultValue="") String demandStatus,
                                 @RequestParam(name="createrId", defaultValue="") String createrId,
                                 @RequestParam(name="sourceBy", defaultValue="") String sourceBy

                                 ) {
        List<Need> needMyList=null;
        try {
            needMyList = needService.NeedMyLists(demandContent,cqNum,createStartTime,createEndTime,demandTypeId,demandStatus,createrId,sourceBy);

        }catch (Exception e){
            e.printStackTrace();
        }
        return needMyList;



    }
    /**
     * 需求新增
     */
    @Log(title = "需求新增", businessType = BusinessType.INSERT)
    @PostMapping("/needAddlist")
    @ApiOperation(value = "需求新增", notes = "工作量管理-需求新增")
    @ResponseBody
    public AjaxResult NeedAddList( Need need) {
        Map<String,String> map =new HashMap<>();
        try{
            //新增需求
            //查询当前用户的id
            User user = getSysUser();
            Long userId = user.getUserId();//拿到创建者用户id
            String userName = user.getUserName();//创建者用户的名字
            String userid = String.valueOf(userId);
            need.setCreaterId(userid);
            need.setCreaterName(userName);
            int n=needService.NeedAddList(need);
            if(n==0 ){
                return error("需求新增必填数据不能为空！");

            }else if(n==999){
                return error("需求描述不能超过500字！");
            }else if(n==777){
                return error("需求内容不能有/ \\ : * ? < > |");
            }

            //新增需求负责人员
            int i=needService.NeedAddUser(need);
            //获取need里多个负责人员根据工作量模型表获取编码存入工作量表(逻辑修改后需求不往工作量表里插入）
            //int j=needService.NeedAddWorkload(need);
            if(n>0){
                return success();
            }
        }catch (Exception e){
            log.error("需求新增失败！", e);
            return error(e.getMessage());

        }
        return error();




    }

    /**
     * 需求新增需要获取需求类型
     */
    @PostMapping("/NeedType")
    @ApiOperation(value = "需求新增需要获取需求类型", notes = "工作量管理-需求新增需要获取需求类型")
    @ResponseBody
    public List<Need> NeedType() {
        //展示所有的需求类型
        List<Need> needAddList = needService.NeedType();
        return needAddList;

    }
    /**
     * 需求新增需要获取需求来源
     */
    @PostMapping("/NeedSources")
    @ApiOperation(value = "需求新增需要获取需求来源", notes = "工作量管理-需求新增需要获取需求来源")
    @ResponseBody
    public List<Need> NeedSources() {
        //展示所有的需求类型
        List<Need> NeedSourcesList = needService.NeedSources();
        return NeedSourcesList;

    }
    /**
     * 需求新增需要负责人员
     */
    @PostMapping("/NeedOwner")
    @ApiOperation(value = "需求新增需要负责人员", notes = "工作量管理-需求新增需要负责人员")
    @ResponseBody
    public Map NeedOwner() {
        //展示所有的需要负责人员(全部)
        List<Need> NeedOwnerList = needService.NeedOwner();
        Need s=new Need();
        Need dataItem;
        Map<String,List<Need>> map=new HashMap<>();
        for ( int  i= 0 ;i<NeedOwnerList.size();i++){
            dataItem = NeedOwnerList.get(i);
            String roleId= dataItem.getRoleId();
            if (map.containsKey(dataItem.getRoleId())){
                map.get(roleId).add(dataItem);
            } else {
                List<Need> list =  new  ArrayList<Need>();
                list.add(dataItem);

                if(!StringUtils.isEmpty(roleId)){
                    map.put(roleId,list);

                }
            }
        }


        return map;
    }
    /**
     * 修改展示根据id需求
     */
    @PostMapping("/needShowUpdate")
    @ApiOperation(value = "修改需求展示", notes = "工作量管理-修改需求展示")
    @ResponseBody
    public Map needShowUpdate(@RequestParam(name="demandId", defaultValue="")  String demandId) {
        Map<String,Object> map=new HashMap<>();
        int i=0;
        try{
            //返回指定需求id的需求
            List<Need> thisneed=needService.needShowUpdate(demandId);

            //返回指定的需求负责人
            List<Need> thisPepole=needService.needShowPepole(demandId);
            //返回指定需求的相关人员
            //返回指定需求的相关人员
            String thatXg=needService.needShowXg(demandId);
            List<Need> thisXg = new ArrayList<>();
            if(!StringUtils.isEmpty(thatXg)){
                String[] split = thatXg.split(",");
                for (String userid : split) {
                    Need need=new Need();
                    //拿到id去获取相关人员名称
                    List<Need> name=needService.needUsername(userid);
                    for (Need sname:name) {
                        need.setUserId(sname.getUserId());
                        need.setUserName(sname.getUserName());
                        thisXg.add(i,need);
                        i++;
                    }

                }

            }
            map.put("thisneed",thisneed);
            map.put("thisPepole",thisPepole);
            map.put("thisXg",thisXg);
        }catch (Exception e){
            log.error("修改展示需求失败！", e);

        }
        return map;

    }
    /**
     * 修改需求
     */
    @PostMapping("/needUpdate")
    @ApiOperation(value = "修改需求", notes = "工作量管理-修改需求")
    @ResponseBody
    public AjaxResult needUpdate( Need need) {
        try{
            String onlineTime = need.getOnlineTime();
            if(StringUtils.isEmpty(onlineTime)){
                need.setOnlineTime("");//上线时间为空，防止null
            }
            //查询需求的创建人
            List<Export> exports = needService.selectNeedList(need);
            //修改需求
            int n=needService.NeedUpdateList(need);
            if(n==0 ){
                return error("需求新增必填数据不能为空！");

            }else if(n==999){
                return error("需求描述不能超过500字！");
            }else if(n==777){
                return error("需求内容不能有/ \\ : * ? < > |");
            }
            //修改需求负责人员
            int i=needService.NeedUpdateUser(need);
            //获取need里多个负责人员根据工作量模型表获取编码存入工作量表
            //int j=needService.NeedUpdateWorkload(need);
            if(n>0 ){
                return success();
            }

        }catch (Exception e){
            log.error("需求修改失败！", e);

        }
        return error();

    }
    /**
     * 需求创建人
     */
    @PostMapping("/needCreator")
    @ApiOperation(value = "需求创建人", notes = "工作量管理-需求创建人")
    @ResponseBody
    public List<Need> needCreator() {
        List<Need> thisList=new ArrayList();
        try{

                thisList=needService.needCreator();
                if(thisList!=null && thisList.size()>0){
                    return thisList;
                }
        }catch (Exception e){
            log.error("需求查看失败！", e);
        }
        return thisList;

    }
    /**
     * 需求创建人
     */
    @PostMapping("/needContact")
    @ApiOperation(value = "需求相关人员", notes = "工作量管理-需求相关人员")
    @ResponseBody
    public List<Need> needContact() {
        List<Need> thisList=new ArrayList();
        try{

            thisList=needService.needContact();
            if(thisList!=null && thisList.size()>0){
                return thisList;
            }
        }catch (Exception e){
            log.error("需求相关人员查看失败！", e);
        }
        return thisList;

    }

    @PostMapping("/export")
    @ApiOperation(value = "导出我的需求", notes = "工作量管理-导出我的需求")
    @ResponseBody
    public AjaxResult export(@RequestParam(name="demandContent", defaultValue="") String demandContent,
                             @RequestParam(name="cqNum", defaultValue="") String cqNum,
                             @RequestParam(name="createStartTime", defaultValue="") String createStartTime,
                             @RequestParam(name="createEndTime", defaultValue="") String createEndTime,
                             @RequestParam(name="demandTypeId", defaultValue="") String demandTypeId,
                             @RequestParam(name="demandStatus", defaultValue="") String demandStatus,
                             @RequestParam(name="createrId", defaultValue="") String createrId,
                             @RequestParam(name="sourceBy", defaultValue="") String sourceBy,
                             @RequestParam(name="relevant", defaultValue="") String relevant,
                             @RequestParam(name="people", defaultValue="") String people){
        try{
            //我的需求查询
            List<Need> needs = NeedMyList(demandContent, cqNum, createStartTime, createEndTime, demandTypeId, demandStatus, createrId, sourceBy,relevant,people);
            List<Export> listExport = new ArrayList<>();
            int i=0;
            for (Need n:needs) {
                Export export=new Export();
                export.setOnlineTime(n.getOnlineTime());
                export.setCreateTime(n.getCreateTime());
                export.setDemandStatus(n.getDemandStatus());
                export.setCqNum(n.getCqNum());
                export.setExpectWorkload(n.getExpectWorkload());
                export.setCreaterName(n.getCreaterName());
                export.setSourceBy(n.getSourceBy());
                export.setSourceType(n.getSourceType());
                export.setDemandType(n.getDemandTypeName());
                export.setDemandContent(n.getDemandContent());

                listExport.add(i,export);
                i++;

            }
            if(listExport!=null && listExport.size()>0) {
                for (Export s : listExport) {
                    String status = s.getDemandStatus();
                    String createTime = util.strToDateFormat(s.getCreateTime());
                    String onlineTime = util.strToDateFormat(s.getOnlineTime());
                    s.setCreateTime(createTime);
                    s.setOnlineTime(onlineTime);
                    if (status.equals("0")) {
                        s.setDemandStatus("新建");
                    } else if (status.equals("1")) {
                        s.setDemandStatus("进行中");

                    } else if (status.equals("2")) {
                        s.setDemandStatus("已上线");

                    } else {
                        s.setDemandStatus("关闭");

                    }

                }
                ExcelUtil<Export> util = new ExcelUtil<Export>(Export.class);
                return util.exportExcel(listExport, "需求清单");
            }
            else
            {
                return error("需求数据为空不能导出,请重新确认是否有数据！");
            }

        }catch (Exception e){
            log.error("需求导出失败,请先关闭导出的需求文档在重试！", e);
        }
        return error("需求导出失败,请先关闭导出的需求文档在重试！");



    }

    @PostMapping("/exportAll")
    @ApiOperation(value = "导出全部需求", notes = "工作量管理-导出全部需求")
    @ResponseBody
    public AjaxResult exportAll(@RequestParam(name="demandContent", defaultValue="") String demandContent,
                             @RequestParam(name="cqNum", defaultValue="") String cqNum,
                             @RequestParam(name="createStartTime", defaultValue="") String createStartTime,
                             @RequestParam(name="createEndTime", defaultValue="") String createEndTime,
                             @RequestParam(name="demandTypeId", defaultValue="") String demandTypeId,
                             @RequestParam(name="demandStatus", defaultValue="") String demandStatus,
                             @RequestParam(name="createrId", defaultValue="") String createrId,
                             @RequestParam(name="sourceBy", defaultValue="") String sourceBy)
    {
        try{
           List<Export> listExport = new ArrayList<>();

            int i=0;
            List<Need> needs = NeedLists(demandContent, cqNum, createStartTime, createEndTime, demandTypeId, demandStatus, createrId, sourceBy);
            for (Need n:needs) {
                Export export=new Export();
                export.setOnlineTime(n.getOnlineTime());
                export.setCreateTime(n.getCreateTime());
                export.setDemandStatus(n.getDemandStatus());
                export.setCqNum(n.getCqNum());
                export.setExpectWorkload(n.getExpectWorkload());
                export.setCreaterName(n.getCreaterName());
                export.setSourceBy(n.getSourceBy());
                export.setSourceType(n.getSourceType());
                export.setDemandType(n.getDemandTypeName());
                export.setDemandContent(n.getDemandContent());

                listExport.add(i,export);
                i++;

            }
            if(listExport!=null && listExport.size()>0){
                for (Export s:listExport) {
                    String status = s.getDemandStatus();
                    String createTime = util.strToDateFormat(s.getCreateTime());
                    String onlineTime = util.strToDateFormat(s.getOnlineTime());
                    s.setCreateTime(createTime);
                    s.setOnlineTime(onlineTime);
                    if(status.equals("0")){
                        s.setDemandStatus("新建");
                    }else if(status.equals("1")){
                        s.setDemandStatus("进行中");

                    }else if(status.equals("2")){
                        s.setDemandStatus("已上线");

                    }else{
                        s.setDemandStatus("关闭");

                    }

                }
                ExcelUtil<Export> util = new ExcelUtil<Export>(Export.class);
                return util.exportExcel(listExport, "需求清单");
            }else
            {
                return error("需求数据为空不能导出,请重新确认是否有数据！");
            }


        }catch (Exception e)
        {
            log.error("需求导出失败,请先关闭导出的需求文档在重试！", e);

        }
        return error("需求导出失败,请先关闭导出的需求文档在重试！");

    }







}
