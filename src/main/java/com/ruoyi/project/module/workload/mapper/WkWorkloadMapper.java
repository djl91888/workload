package com.ruoyi.project.module.workload.mapper;

import com.ruoyi.project.module.workload.domain.WorkloadRequestBean;
import com.ruoyi.project.module.workload.domain.WorkloadResponseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 工作量Mapper接口
 * 
 * @author ruoyi
 * @date 2020-11-13
 */
public interface WkWorkloadMapper 
{

    /**
     * 根据用户id查询工作清单
     * @param bean
     * @return
     */
    public List<WorkloadResponseBean> findWorkload(WorkloadRequestBean bean);

    /**
     * 根据查询核算基本信息
     * @param demandId
     * @return
     */
    public WorkloadResponseBean queryDemandById(@Param("demandId")String demandId);

    /**
     * 根据工作量id查询工作量
     * @param workloadId
     * @return
     */
    public WorkloadResponseBean getWorkloadById(@Param("workloadId")String workloadId);

    /**
     * 查询当月工作日天数
     * @param time
     * @return
     */
    public Map<String,String> getDay(String time);

    /**
     * 根据工作量id修改工作量
     * @param difficultDegree
     * @param workNum
     * @param workTime
     * @param manMonth
     * @param workContent
     * @param workloadId
     * @param outputName
     * @param outputUrl
     * @param hasOutput
     * @return
     */
    public int updateWorkloadById(
            @Param("difficultDegree")String difficultDegree,
            @Param("workNum")String workNum,
            @Param("workTime")String workTime,
            @Param("manMonth")String manMonth,
            @Param("workContent")String workContent,
            @Param("workText")String workText,
            @Param("workloadId")String workloadId,
            @Param("outputName")String outputName,
            @Param("outputUrl")String outputUrl,
            @Param("hasOutput")String hasOutput
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
    public List<WorkloadResponseBean> findNote(
            @Param("demandId")String demandId,
            @Param("bigTypeId")String bigTypeId,
            @Param("smallTypeId")String smallTypeId,
            @Param("workDate")String workDate,
            @Param("modelId")String modelId
    );

    /**
     * 根据需求id/当前时间/大类id/角色id 查询现有小类
     * @param bigTypeId
     * @param roleId
     * @param demandId
     * @param workDate
     * @return
     */
    public List<WorkloadResponseBean> findSmallType(
            @Param("bigTypeId")String bigTypeId,
            @Param("roleId")String roleId,
            @Param("demandId")String demandId,
            @Param("workDate")String workDate
    );


    /**
     * 根据需求id/当前时间/小类id/角色id 查询现有工作项
     * @param smallTypeId
     * @param roleId
     * @param demandId
     * @param workDate
     * @return
     */
    public List<WorkloadResponseBean> findWorkItem(
            @Param("smallTypeId")String smallTypeId,
            @Param("roleId")String roleId,
            @Param("demandId")String demandId,
            @Param("workDate")String workDate
    );

    /**
     * 更新文件名称
     * @param fileName
     * @param workloadId
     * @param hasOutput
     * @return
     */
    public int updateFileName(@Param("fileName")String fileName,@Param("workloadId")String workloadId,@Param("hasOutput")String hasOutput);

    /**
     * 查询文件名称
     * @param workloadId
     * @return
     */
    public Map<String,String> getFileName(String workloadId);


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
