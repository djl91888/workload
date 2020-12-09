package com.ruoyi.project.module.note.mapper;

import com.ruoyi.project.module.model.domain.WkModel;
import com.ruoyi.project.module.note.domain.WkNote;
import com.ruoyi.project.module.note.domain.WorkNote;
import com.ruoyi.project.module.type.domain.WkType;
import com.ruoyi.project.module.workload.domain.WkWorkload;

import java.util.List;
import java.util.Map;

/**
 * 笔记Mapper接口
 *
 * @author yuxin
 * @date 2020-11-12
 */
public interface WkNoteMapper {

    /**
     * 查询笔记列表（展示用）
     *
     * @param wkNote 筛选条件
     * @return 笔记列表
     */
    List<WorkNote> selectWorkNotes(WorkNote wkNote);

    /**
     * 查询具体笔记（展示用）
     *
     * @param noteId 笔记id
     * @return 笔记
     */
    WorkNote selectWorkNoteById(String noteId);

    /**
     * 查询需求类型列表（全部）
     *
     * @return 需求类型列表
     */
    List<Map<String, String>> selectDemandTypeList();

    /**
     * 查询需求列表（全部/角色限制）
     *
     * @param demandTypeId 需求类型id
     * @param userId       用户id
     * @return 需求列表
     */
    List<Map<String, String>> selectDemandList(String demandTypeId, String userId);

    /**
     * 查询工作大类列表（角色限制）
     * 目前工作大类列表不作限制，故未使用
     *
     * @param roleId 角色id
     * @return 工作大类列表
     */
    List<WkType> selectWorkCategories(String roleId);

    /**
     * 查询工作小类列表（角色限制）
     *
     * @param secLevel 工作大类id
     * @param roleId   角色id
     * @return 工作小类列表
     */
    List<WkType> selectWorkTypes(String secLevel, String roleId);

    /**
     * 查询工作项列表（角色限制）
     *
     * @param workTypeId 工作小类id
     * @param roleId     角色id
     * @return 工作项列表
     */
    List<WkModel> selectWorkItems(String workTypeId, String roleId);

    /**
     * 查询具体工作量
     *
     * @param workDate 工作日期（6位）
     * @param demandId 需求id
     * @param modelId  模型id
     * @return 工作量
     */
    WkWorkload selectWorkload(String workDate, String demandId, String modelId);

    /**
     * 查询具体工作量
     *
     * @param noteId 笔记id
     * @return 工作量
     */
    WkWorkload selectWorkloadByNoteId(String noteId);

    /**
     * 插入工作量
     *
     * @param wkWorkload 参数
     * @return 影响行数
     */
    int insertWorkload(WkWorkload wkWorkload);

    /**
     * 更新工作量
     *
     * @param wkWorkload 参数
     * @return 影响行数
     */
    int updateWorkload(WkWorkload wkWorkload);

    /**
     * 删除工作量
     *
     * @param workloadId 工作量id
     * @return 影响行数
     */
    int deleteWorkload(String workloadId);

    /**
     * 查询具体笔记
     *
     * @param noteId 笔记id
     * @return 笔记
     */
    WkNote selectWkNoteById(String noteId);

    /**
     * 插入笔记
     *
     * @param wkNote 参数
     * @return 影响行数
     */
    int insertWkNote(WkNote wkNote);

    /**
     * 更新笔记
     *
     * @param wkNote 参数
     * @return 影响行数
     */
    int updateWkNote(WkNote wkNote);

    /**
     * 删除笔记
     *
     * @param noteId 笔记id
     * @return 影响行数
     */
    int deleteWkNoteById(String noteId);

}
