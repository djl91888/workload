package com.ruoyi.project.module.workload.service;

import com.ruoyi.project.module.workload.domain.WorkloadRequestBean;
import com.ruoyi.project.module.workload.domain.WorkloadResponseBean;

import java.util.List;
import java.util.Map;

/**
 * 工作量Service接口
 * 
 * @author ruoyi
 * @date 2020-11-13
 */
public interface IWkWorkloadService 
{
    /**
     * 根据用户查询工作量
     * @param bean
     * @return
     */
    public List<WorkloadResponseBean> findWorkload(WorkloadRequestBean bean);

    /**
     * 根据需求id查询基本信息
     * @param demandId
     * @return
     */
    public WorkloadResponseBean queryDemandById(String demandId);

    /**
     * 根据工作量id查询工作量
     * @param workloadId
     * @return
     */
    public WorkloadResponseBean getWorkloadById(String workloadId);

    /**
     *
     * @param difficultDegree
     * @param workNum
     * @param workTime
     * @param manMonth
     * @param workContent
     * @param workText
     * @param workloadId
     * @param fileName
     * @param fileUrl
     * @param hasOutput
     * @return
     */
    public int updateWorkloadById(String difficultDegree,
                                  String workNum,
                                  String workTime,
                                  String manMonth,
                                  String workContent,
                                  String workText,
                                  String workloadId,
                                  String fileName,
                                  String fileUrl,
                                  String hasOutput
    );

    /**
     * 根据当前月份/需求id/工作大类/工作小类/工作项----查询笔记
     * @param demandId
     * @param bigTypeId
     * @param smallTypeId
     * @param workDate
     * @param modelId
     * @return
     */
    public List<WorkloadResponseBean> findNote(String demandId,
                                               String bigTypeId,
                                               String smallTypeId,
                                               String workDate,
                                               String modelId);

    /**
     * 根据需求id/当前时间/大类id/角色id 查询现有小类
     * @param bigTypeId
     * @param roleId
     * @param demandId
     * @param workDate
     * @return
     */
    public List<WorkloadResponseBean> findSmallType(String bigTypeId,
                                                    String roleId,
                                                    String demandId,
                                                    String workDate);

    /**
     * 根据需求id/当前时间/小类id/角色id 查询现有工作项
     * @param smallTypeId
     * @param roleId
     * @param demandId
     * @param workDate
     * @return
     */
    public List<WorkloadResponseBean> findWorkItem(String smallTypeId,
                                                    String roleId,
                                                    String demandId,
                                                    String workDate);

    /**
     * 获取五级路径（/需求类型/需求名称/工作大类/工作小类/工作项）
     * @param time
     * @param demandid
     * @param modelid
     * @return
     */
    public  String filePath(String time,String demandid,String modelid);


    /**
     * 更新文件名称
     * @param fileName
     * @param workloadId
     * @param hasOutput
     * @return
     */
    public int updateFileName(String fileName,String workloadId,String hasOutput);


    /**
     * 查询文件名称
     * @param workloadId
     * @return
     */
    public Map<String, String> getFileName(String workloadId);



    /**
     * 查询工作小类
     * @return
     */
    public List<WorkloadResponseBean> querySmallTypes();

    /**
     * 导出工作量
     * @param bean
     * @return
     */
    public List<WorkloadResponseBean> exlWorkload(WorkloadRequestBean bean);
}
