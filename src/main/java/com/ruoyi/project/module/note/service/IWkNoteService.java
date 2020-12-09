package com.ruoyi.project.module.note.service;

import com.ruoyi.project.module.model.domain.WkModel;
import com.ruoyi.project.module.note.domain.UploadRequest;
import com.ruoyi.project.module.note.domain.WkNote;
import com.ruoyi.project.module.note.domain.WorkNote;
import com.ruoyi.project.module.type.domain.WkType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 笔记Service接口
 */
public interface IWkNoteService {

    /**
     * 查询用户是否是主管
     *
     * @return 用户是否是主管
     */
    boolean isSupervisor();

    /**
     * 查询需求类型列表（全部）
     *
     * @return 需求类型列表
     */
    List<Map<String, String>> getDemandTypeList();

    /**
     * 查询需求列表（全部/角色限制）
     *
     * @return 需求列表
     */
    List<Map<String, String>> getDemandList(String demandTypeId);

    /**
     * 查询工作大类列表（全部）
     *
     * @return 工作大类列表
     */
    List<WkType> getWorkCategories();

    /**
     * 查询工作小类列表（全部/角色限制）
     *
     * @param secLevel 工作大类id
     * @return 工作小类列表
     */
    List<WkType> getWorkTypes(String secLevel);

    /**
     * 查询工作项列表（全部/角色限制）
     *
     * @param workTypeId 工作小类id
     * @return 工作项列表
     */
    List<WkModel> getWorkItems(String workTypeId);

    /**
     * 查询笔记列表
     *
     * @param workNote 筛选条件
     * @return 笔记列表
     */
    List<WorkNote> getWorkNoteList(WorkNote workNote);

    /**
     * 查询笔记详情
     *
     * @param noteId 笔记id
     * @return 笔记
     */
    WkNote getWorkNoteById(String noteId);

    /**
     * 上传文件
     *
     * @param uploadRequest 路径
     * @param files         上传的文件数组
     * @return 是否成功、存放路径、文件名称
     */
    WkNote upload(UploadRequest uploadRequest, MultipartFile[] files);

    /**
     * 新建笔记
     *
     * @param wkNote 参数
     * @return 结果
     */
    Map<String, String> addWkNote(WkNote wkNote);

    /**
     * 修改笔记
     *
     * @param wkNote 参数
     * @return 结果
     */
    Map<String, String> changeWkNote(WkNote wkNote);

    /**
     * 删除笔记
     *
     * @param noteId 笔记id
     * @return 结果
     */
    Map<String, String> removeWkNoteById(String noteId);

}
