package com.ruoyi.project.module.workload.service.impl;

import com.ruoyi.common.utils.file.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.module.workload.domain.WorkloadRequestBean;
import com.ruoyi.project.module.workload.domain.WorkloadResponseBean;
import com.ruoyi.project.module.workload.mapper.WkWorkloadMapper;
import com.ruoyi.project.module.workload.service.IWkWorkloadService;
import com.ruoyi.project.need.mapper.NeedMapper;
import com.ruoyi.project.system.role.domain.Role;
import com.ruoyi.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工作量Service业务层处理
 *
 * @author ruoyi
 * @date 2020-11-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WkWorkloadServiceImpl implements IWkWorkloadService
{
    @Autowired
    private WkWorkloadMapper wkWorkloadMapper;

    @Autowired
    private NeedMapper needMapper;

    @Override
    public List<WorkloadResponseBean> findWorkload(WorkloadRequestBean bean) {
        if (bean != null){
            if(!"isAll".equals(bean.getIsAll())){
                User user = ShiroUtils.getSysUser();
                bean.setUserId(String.valueOf(user.getUserId()));
                List<Role> list = user.getRoles();
                for (Role role:list) {
                    // 查询我的清单
                    if(!"主管".equals(role.getRoleName())){
                        String roleId = String.valueOf(role.getRoleId());
                        bean.setRoleId(roleId);
                        break;
                    }
                }
            }
            if(StringUtils.isFullNull(bean.getWorkDate())){
                SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
                bean.setWorkDate(format.format(new Date()));
            }
            return wkWorkloadMapper.findWorkload(bean);
        }
        return null;
    }

    @Override
    public WorkloadResponseBean queryDemandById(String demandId) {
        return wkWorkloadMapper.queryDemandById(demandId);
    }

    @Override
    public WorkloadResponseBean getWorkloadById(String workloadId) {
        return wkWorkloadMapper.getWorkloadById(workloadId);
    }

    @Override
    public int updateWorkloadById(String difficultDegree, String workNum, String workTime, String manMonth, String workContent,String workText, String workloadId, String fileName,
                                  String fileUrl, String hasOutput) {
        return wkWorkloadMapper.updateWorkloadById(difficultDegree, workNum, workTime, manMonth, workContent,workText,  workloadId,fileName,fileUrl,hasOutput);
    }

    @Override
    public List<WorkloadResponseBean> findNote(String demandId, String bigTypeId, String smallTypeId, String workDate, String modelId) {
        return wkWorkloadMapper.findNote(demandId, bigTypeId, smallTypeId, workDate, modelId);
    }

    @Override
    public List<WorkloadResponseBean> findSmallType(String bigTypeId, String roleId, String demandId, String workDate) {
        return wkWorkloadMapper.findSmallType(bigTypeId, roleId, demandId, workDate);
    }

    @Override
    public List<WorkloadResponseBean> findWorkItem(String smallTypeId, String roleId, String demandId, String workDate) {
        return wkWorkloadMapper.findWorkItem(smallTypeId, roleId, demandId, workDate);
    }

    @Override
    public String filePath(String time,String demandid, String modelid) {
        String typename="";
        String demandName="";
        String workItem="";
        String workBigType="";
        String worksmallType="";
        String filePath="";
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
//        String time = format.format(new Date());
        String index="/工作量"+time+"/";
        //查询需求相关
        if(!StringUtils.isFullNull(demandid)){
            Map<String, String> map=needMapper.selectNeed(demandid);
            if(map != null && !map.isEmpty()){
                typename = map.get("TYPE_NAME");
                demandName = map.get("DEMAND_CONTENT");
            }
            if(!StringUtils.isFullNull(modelid)){
                //查询工作项大类
                workBigType = needMapper.selectBtypename(modelid);//工作大类
                if(StringUtils.isFullNull(workBigType)){
                    workBigType="";
                }
                Map<String, String> stypename = needMapper.selectStypename(modelid);//小类和工作项
                if(stypename!=null && !stypename.isEmpty()){
//                    workItem = stypename.get("WORK_ITEM");//工作项
                    worksmallType = stypename.get("TYPE_NAME");//工作小类
                }
            }
            //拼接路径（和生活功能/摇一摇新需求/支撑/程序开发/接口开发/登录流程.txt）
            filePath=typename+"/"+demandName+"/"+workBigType+"/"+worksmallType;
            String[] split = filePath.split("/");
            for (String s:split) {
                if(StringUtils.isFullNull(s)){
                    s="";
                    continue;
                }
                index+=s+"/";
            }
        }
        return index;
    }

    @Override
    public int updateFileName(String fileName, String workloadId, String hasOutput) {
        return wkWorkloadMapper.updateFileName(fileName, workloadId,hasOutput);
    }

    @Override
    public Map<String, String> getFileName(String workloadId) {
        return wkWorkloadMapper.getFileName(workloadId);
    }

    @Override
    public List<WorkloadResponseBean> querySmallTypes() {
        return wkWorkloadMapper.querySmallTypes();
    }



    @Override
    public List<WorkloadResponseBean> exlWorkload(WorkloadRequestBean bean) {
        if (bean != null){
            if(!"isAll".equals(bean.getIsAll())){
                User user = ShiroUtils.getSysUser();
                bean.setUserId(String.valueOf(user.getUserId()));
                List<Role> list = user.getRoles();
                for (Role role:list) {
                    // 查询我的清单
                    if(!"主管".equals(role.getRoleName())){
                        String roleId = String.valueOf(role.getRoleId());
                        bean.setRoleId(roleId);
                        break;
                    }
                }
            }
            if(StringUtils.isFullNull(bean.getWorkDate())){
                SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
                bean.setWorkDate(format.format(new Date()));
            }
            return wkWorkloadMapper.exlWorkload(bean);
        }
        return null;
    }

}
