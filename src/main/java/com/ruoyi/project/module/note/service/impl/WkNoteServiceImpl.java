package com.ruoyi.project.module.note.service.impl;

import com.ruoyi.common.utils.file.FTPUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.module.model.domain.WkModel;
import com.ruoyi.project.module.model.mapper.WkModelMapper;
import com.ruoyi.project.module.note.domain.UploadRequest;
import com.ruoyi.project.module.note.domain.WkNote;
import com.ruoyi.project.module.note.domain.WorkNote;
import com.ruoyi.project.module.note.mapper.WkNoteMapper;
import com.ruoyi.project.module.note.service.IWkNoteService;
import com.ruoyi.project.module.type.domain.WkType;
import com.ruoyi.project.module.type.mapper.WkTypeMapper;
import com.ruoyi.project.module.workload.domain.WkWorkload;
import com.ruoyi.project.system.role.domain.Role;
import com.ruoyi.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 笔记Service业务层处理
 *
 * @author yuxin
 * @date 2020-11-12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WkNoteServiceImpl implements IWkNoteService {

    @Autowired
    private WkNoteMapper noteMapper;

    @Autowired
    private WkTypeMapper typeMapper;

    @Autowired
    private WkModelMapper modelMapper;

    /**
     * 获取用户角色id列表
     *
     * @return 用户角色id列表
     */
    private List<Long> getRoleIds() {
        User user = ShiroUtils.getSysUser();
        List<Role> roles = user.getRoles();
        List<Long> roleIds = new ArrayList<>();
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                Long roleId = role.getRoleId();
                roleIds.add(roleId);
            }
        }
        return roleIds;
    }

    /**
     * 查询用户是否是主管
     *
     * @return 用户是否是主管
     */
    @Override
    public boolean isSupervisor() {
        User user = ShiroUtils.getSysUser();
        Long userId = user.getUserId();
        if (userId == 1L) return true;
        List<Long> roleIds = getRoleIds();
        return roleIds.contains(120L);
    }

    /**
     * 查询需求类型列表（全部）
     *
     * @return 需求类型列表
     */
    @Override
    public List<Map<String, String>> getDemandTypeList() {
        return noteMapper.selectDemandTypeList();
    }

    /**
     * 查询需求列表（全部/角色限制）
     *
     * @return 需求列表
     */
    @Override
    public List<Map<String, String>> getDemandList(String demandTypeId) {
        String userId = null;
        boolean isSupervisor = isSupervisor();
        if (!isSupervisor) {
            Long userIdL = ShiroUtils.getUserId();
            userId = String.valueOf(userIdL);
        }
        return noteMapper.selectDemandList(demandTypeId, userId);
    }

    /**
     * 查询工作大类列表（全部）
     *
     * @return 工作大类列表
     */
    @Override
    public List<WkType> getWorkCategories() {
        WkType wkType = new WkType();
        wkType.setTypeLevel("2");
        return typeMapper.selectWkTypeList(wkType);
    }

    /**
     * 查询工作小类列表（全部/角色限制）
     *
     * @param secLevel 工作大类id
     * @return 工作小类列表
     */
    @Override
    public List<WkType> getWorkTypes(String secLevel) {
        List<WkType> workTypes;
        boolean isSupervisor = isSupervisor();
        if (isSupervisor) {
            WkType wkType = new WkType();
            wkType.setTypeLevel("3").setSecLevel(secLevel);
            workTypes = typeMapper.selectWkTypeList(wkType);
        } else {
            Long roleId = getRoleIds().get(0);
            workTypes = noteMapper.selectWorkTypes(secLevel, String.valueOf(roleId));
        }
        return workTypes;
    }

    /**
     * 查询工作项列表（全部/角色限制）
     *
     * @param workTypeId 工作小类id
     * @return 工作项列表
     */
    @Override
    public List<WkModel> getWorkItems(String workTypeId) {
        List<WkModel> workModels;
        boolean isSupervisor = isSupervisor();
        if (isSupervisor) {
            WkModel wkModel = new WkModel();
            wkModel.setWorkType(workTypeId);
            workModels = modelMapper.selectWkModelList(wkModel);
        } else {
            Long roleId = getRoleIds().get(0);
            workModels = noteMapper.selectWorkItems(workTypeId, String.valueOf(roleId));
        }
        return workModels;
    }

    /**
     * 查询笔记列表
     *
     * @param workNote 筛选条件
     * @return 笔记列表
     */
    @Override
    public List<WorkNote> getWorkNoteList(WorkNote workNote) {
        int showType = workNote.getShowType();
        User user = ShiroUtils.getSysUser();
        Long userId = user.getUserId();
        // showType 0只展示我的笔记，1展示全部笔记
        // 如果展示全部笔记，则不根据用户id筛选
        if (showType == 1) workNote.setUserId(null);
        else workNote.setUserId(String.valueOf(userId));
        return noteMapper.selectWorkNotes(workNote);
    }

    /**
     * 查询笔记详情
     *
     * @param noteId 笔记id
     * @return 笔记
     */
    @Override
    public WkNote getWorkNoteById(String noteId) {
        return noteMapper.selectWkNoteById(noteId);
    }

    /**
     * 校验上传文件接口的必要参数是否为空
     *
     * @param uploadRequest 参数
     * @return 参数是否为空
     */
    private boolean uploadParamIsNull(UploadRequest uploadRequest) {
        String demandType = uploadRequest.getDemandType();
        String demandContent = uploadRequest.getDemandContent();
        String workCategory = uploadRequest.getWorkCategory();
        String workType = uploadRequest.getWorkType();
        String workDate = uploadRequest.getWorkDate();
        return StringUtils.isEmpty(demandType) || StringUtils.isEmpty(demandContent)
                || StringUtils.isEmpty(workCategory) || StringUtils.isEmpty(workType) || StringUtils.isEmpty(workDate);
    }

    /**
     * 上传文件
     *
     * @param uploadRequest 路径
     * @param files         上传的文件数组
     * @return 是否成功、存放路径、文件名称
     */
    @Override
    public WkNote upload(UploadRequest uploadRequest, MultipartFile[] files) {
        WkNote wkNote = new WkNote();
        if (uploadParamIsNull(uploadRequest)) {
            wkNote.setHasOutput("0");
            wkNote.setParam1("必要参数为空");
            return wkNote;
        }
        if (files == null || files.length == 0) {
            wkNote.setHasOutput("0");
            wkNote.setParam1("未选择文件");
            return wkNote;
        }
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            if (StringUtils.isEmpty(fileName)) {
                wkNote.setHasOutput("0");
                wkNote.setParam1("文件名不能为空");
                return wkNote;
            }
            if (fileName.contains(",")) {
                wkNote.setHasOutput("0");
                wkNote.setParam1("文件名不能包含逗号");
                return wkNote;
            }
        }
        String workDate = uploadRequest.getWorkDate();
        workDate = workDate.replaceAll("-", "");
        Pattern pattern = Pattern.compile("^202\\d(0\\d|1[0-2])[0-3]\\d$");
        if (!pattern.matcher(workDate).matches()) {
            wkNote.setHasOutput("0");
            wkNote.setParam1("日期格式不合法");
            return wkNote;
        }
        String demandType = uploadRequest.getDemandType();
        String demandContent = uploadRequest.getDemandContent();
        String workCategory = uploadRequest.getWorkCategory();
        String workType = uploadRequest.getWorkType();
        String workMonth = workDate.substring(0, 6);
        String rootName = "工作量" + workMonth;
        String path = "/" + rootName + "/" + demandType + "/" + demandContent + "/" + workCategory + "/" + workType;
        boolean success = FTPUtils.upload(files, path);
        if (success) {
            StringBuilder builder = new StringBuilder();
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                builder.append(",").append(fileName);
            }
            wkNote.setHasOutput("1");
            wkNote.setOutputUrl(path);
            wkNote.setOutputName(builder.toString().substring(1));
        } else {
            wkNote.setHasOutput("0");
            wkNote.setParam1("上传失败");
        }
        return wkNote;
    }

    /**
     * 校验新建笔记接口的必要参数是否为空
     *
     * @param wkNote 参数
     * @return 参数是否为空
     */
    private boolean insertParamIsNull(WkNote wkNote) {
        String demandId = wkNote.getDemandId();
        String workCategoryId = wkNote.getWorkCategoryId();
        String workTypeId = wkNote.getWorkTypeId();
        String workModelId = wkNote.getWorkModelId();
        String workHours = wkNote.getWorkHours();
        String workDate = wkNote.getWorkDate();
        String workDateEnd = wkNote.getWorkDateEnd();
        String hasOutput = wkNote.getHasOutput();
        return StringUtils.isEmpty(demandId) || StringUtils.isEmpty(workCategoryId) || StringUtils.isEmpty(workTypeId)
                || StringUtils.isEmpty(hasOutput) || StringUtils.isEmpty(workModelId) || StringUtils.isEmpty(workHours)
                || StringUtils.isEmpty(workDate) || StringUtils.isEmpty(workDateEnd);
    }

    /**
     * 新建笔记
     *
     * @param wkNote 参数
     * @return 结果
     */
    @Override
    public Map<String, String> addWkNote(WkNote wkNote) {

        Map<String, String> result = new HashMap<>();
        boolean paramIsNull = insertParamIsNull(wkNote);
        if (paramIsNull) return fail("必要参数为空");

        // 校验工作时长参数合法性
        String workHours = wkNote.getWorkHours();
        Pattern pattern = Pattern.compile("^100:00|(\\d{2}:[0-5]\\d)$");
        boolean workHourIsLegal = pattern.matcher(workHours).matches();
        if (!workHourIsLegal) return fail("工作时长不合法");

        // 校验工作日期参数合法性
        String workDate = wkNote.getWorkDate();
        String workDateEnd = wkNote.getWorkDateEnd();
        workDate = workDate.replaceAll("-", "");
        workDateEnd = workDateEnd.replaceAll("-", "");
        pattern = Pattern.compile("^202\\d(0\\d|1[0-2])[0-3]\\d$");
        boolean workDateIsLegal = pattern.matcher(workDate).matches();
        boolean workDateEndIsLegal = pattern.matcher(workDateEnd).matches();
        if (!workDateIsLegal || !workDateEndIsLegal) return fail("工作日期格式不合法");
        String startMonth = workDate.substring(4, 6);
        String endMonth = workDateEnd.substring(4, 6);
        if (!startMonth.equals(endMonth)) return fail("工作日期不能跨月");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date workDate1 = sdf.parse(workDate);
            Date workDateEnd1 = sdf.parse(workDateEnd);
            if (workDate1.after(workDateEnd1)) return fail("开始日期不能晚于结束日期");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        wkNote.setWorkDate(workDate);
        wkNote.setWorkDateEnd(workDateEnd);

        // 校验工作内容长度
        String workDesc = wkNote.getWorkDesc();
        if (!StringUtils.isEmpty(workDesc))
            if (workDesc.length() > 500) return fail("工作内容过长");

        // 校验附件名称长度
        String outputName = wkNote.getOutputName();
        if (!StringUtils.isEmpty(outputName)) {
            int length = outputName.getBytes().length;
            if (length > 1000) return fail("附件名称过长");
        }

        // 设置创建人等参数
        User user = ShiroUtils.getSysUser();
        Long userId = user.getUserId();
        String userName = user.getUserName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String createTime = sdf.format(new Date());

        wkNote.setUserId(String.valueOf(userId));
        wkNote.setCreaterId(String.valueOf(userId));
        wkNote.setCreaterName(userName);
        wkNote.setCreateTime(createTime);

        // 插入笔记表
        int row = noteMapper.insertWkNote(wkNote);
        if (row > 0) result.put("status", "0");

        String hasOutput = wkNote.getHasOutput();
        String outputUrl = wkNote.getOutputUrl();

        String workMonth = workDate.substring(0, 6);
        String demandId = wkNote.getDemandId();
        String modelId = wkNote.getWorkModelId();

        // 通过工作月份、需求、工作项查询工作量
        WkWorkload workloadDB = noteMapper.selectWorkload(workMonth, demandId, modelId);
        // 如果没有这条工作量，则插入
        // 加入线程锁，避免线程不安全，否则可能有多条相同数据同时插入工作量表
        synchronized (this) {
            if (workloadDB == null) {
                WkWorkload workload = new WkWorkload();
                workload.setNoteId(wkNote.getNoteId());
                workload.setDemandId(demandId);
                workload.setWorkCategoryId(wkNote.getWorkCategoryId());
                workload.setWorkTypeId(wkNote.getWorkTypeId());
                workload.setWorkModelId(wkNote.getWorkModelId());
                workload.setWorkDate(workDate);
                workload.setHasOutput(hasOutput);
                workload.setOutputUrl(outputUrl);
                workload.setOutputName(outputName);
                workload.setCreaterId(String.valueOf(userId));
                workload.setCreaterName(userName);
                workload.setCreateTime(createTime);
                row = noteMapper.insertWorkload(workload);
                if (row > 0) result.put("status", "0");
            } else {
                // 如果有，则更新该条工作量
                WkWorkload workload = new WkWorkload();
                workload.setWorkloadId(workloadDB.getWorkloadId());
                String noteId = workloadDB.getNoteId();
                noteId += "," + wkNote.getNoteId();
                workload.setNoteId(noteId);
                if ("1".equals(hasOutput)) {
                    String hasOutput1 = workloadDB.getHasOutput();
                    if ("1".equals(hasOutput1)) {
                        String[] split = outputName.split(",");
                        List<String> names = new ArrayList<>();
                        for (String name : split) {
                            if (StringUtils.isEmpty(name)) continue;
                            names.add(name);
                        }
                        StringBuilder builder = new StringBuilder();
                        String outputName1 = workloadDB.getOutputName();
                        for (String name : names)
                            if (!outputName1.contains(name))
                                builder.append(",").append(name);
                        String appenden = builder.toString();
                        if (!StringUtils.isEmpty(appenden))
                            workload.setOutputName(outputName1 + appenden);
                    } else {
                        workload.setHasOutput("1");
                        workload.setOutputUrl(outputUrl);
                        workload.setOutputName(outputName);
                    }
                }
                // 更新工作量
                int row1 = noteMapper.updateWorkload(workload);
                if (row1 > 0) result.put("status", "0");
            }
        }

        return result;
    }

    /**
     * 修改笔记
     *
     * @param wkNote 参数
     * @return 结果
     */
    @Override
    public Map<String, String> changeWkNote(WkNote wkNote) {

        Map<String, String> result = new HashMap<>();
        String noteId = wkNote.getNoteId();
        if (StringUtils.isEmpty(noteId)) return fail("noteId为空");

        WkNote wkNoteDB = noteMapper.selectWkNoteById(noteId);
        if (wkNoteDB == null) return fail("笔记不存在");

        // 校验工作时长参数合法性
        String workHours = wkNote.getWorkHours();
        if (workHours != null) {
            Pattern pattern = Pattern.compile("^100:00|(\\d{2}:[0-5]\\d)$");
            boolean workHourIsLegal = pattern.matcher(workHours).matches();
            if (!workHourIsLegal) return fail("工作时长不合法");
        }

        // 校验工作内容长度
        String workDesc = wkNote.getWorkDesc();
        if (!StringUtils.isEmpty(workDesc))
            if (workDesc.length() > 500) return fail("工作内容过长");

        // 校验附件名称长度
        String outputName = wkNote.getOutputName();
        if (!StringUtils.isEmpty(outputName)) {
            int length = outputName.getBytes().length;
            if (length > 1000) return fail("附件名称过长");
        }

        // 更新笔记的参数
        WkNote wkNote1 = new WkNote();
        wkNote1.setNoteId(noteId);
        wkNote1.setWorkHours(workHours);
        wkNote1.setWorkDesc(workDesc);
        wkNote1.setWorkText(wkNote.getWorkDesc());

        String hasOutput = wkNote.getHasOutput();
        String outputUrl = wkNote.getOutputUrl();

        // 判断是否需要更新附件名称等字段
        if ("1".equals(hasOutput)) {
            String hasOutputDB = wkNoteDB.getHasOutput();
            if ("1".equals(hasOutputDB)) {
                String[] split = outputName.split(",");
                List<String> names = new ArrayList<>();
                for (String name : split) {
                    if (StringUtils.isEmpty(name)) continue;
                    names.add(name);
                }
                String outputNameDB = wkNoteDB.getOutputName();
                StringBuilder builder = new StringBuilder();
                for (String name : names)
                    if (!outputNameDB.contains(name))
                        builder.append(",").append(name);
                String appenden = builder.toString();
                if (!StringUtils.isEmpty(appenden))
                    wkNote1.setOutputName(outputNameDB + appenden);
            } else {
                wkNote1.setHasOutput("1");
                wkNote1.setOutputUrl(outputUrl);
                wkNote1.setOutputName(outputName);
            }
        }

        // 更新笔记
        int row = noteMapper.updateWkNote(wkNote1);
        if (row > 0) result.put("status", "0");

        // 查询这条笔记所关联的工作量
        WkWorkload workload = noteMapper.selectWorkloadByNoteId(noteId);
        String hasOutputWorkload = workload.getHasOutput();
        WkWorkload workload1 = new WkWorkload();
        workload1.setWorkloadId(workload.getWorkloadId());
        boolean updateWorkload = false;

        // 判断是否需要更新工作量
        if ("1".equals(hasOutput)) {
            if ("1".equals(hasOutputWorkload)) {
                String[] split = outputName.split(",");
                List<String> names = new ArrayList<>();
                for (String name : split) {
                    if (StringUtils.isEmpty(name)) continue;
                    names.add(name);
                }
                String outputName1 = workload.getOutputName();
                StringBuilder builder = new StringBuilder();
                for (String name : names)
                    if (!outputName1.contains(name))
                        builder.append(",").append(name);
                String appenden = builder.toString();
                if (!StringUtils.isEmpty(appenden)) {
                    workload1.setOutputName(outputName1 + appenden);
                    updateWorkload = true;
                }
            } else {
                workload1.setHasOutput("1");
                workload1.setOutputUrl(outputUrl);
                workload1.setOutputName(outputName);
                updateWorkload = true;
            }
        }

        // 如果需要更新工作量，则更新
        if (updateWorkload) {
            int row1 = noteMapper.updateWorkload(workload1);
            if (row1 > 0) result.put("status", "0");
        }

        return result;
    }

    /**
     * 删除笔记
     *
     * @param noteId 笔记id
     * @return 结果
     */
    @Override
    public Map<String, String> removeWkNoteById(String noteId) {
        Map<String, String> result = new HashMap<>();
        if (StringUtils.isEmpty(noteId)) return fail("noteId为空");
        WkWorkload workload = noteMapper.selectWorkloadByNoteId(noteId);
        if (workload == null) return fail("该笔记不存在");
        String workloadId = workload.getWorkloadId();
        String noteId1 = workload.getNoteId();
        int row1 = noteMapper.deleteWkNoteById(noteId);
        int row2;
        if (noteId.equals(noteId1)) {
            row2 = noteMapper.deleteWorkload(workloadId);
        } else {
            List<String> ids = new ArrayList<>();
            String[] noteIds = noteId1.split(",");
            for (String id : noteIds) {
                if (StringUtils.isEmpty(id)) continue;
                ids.add(id);
            }
            ids.removeIf(id -> id.equals(noteId));
            StringBuilder builder = new StringBuilder();
            for (String id : ids) builder.append(",").append(id);
            String noteIdNew = builder.toString().substring(1);
            WkWorkload workload1 = new WkWorkload();
            workload1.setWorkloadId(workloadId);
            workload1.setNoteId(noteIdNew);
            row2 = noteMapper.updateWorkload(workload1);
        }
        if (row1 > 0 && row2 > 0) result.put("status", "0");
        return result;
    }

    private Map<String, String> fail(String msg) {
        Map<String, String> result = new HashMap<>();
        result.put("status", "1");
        result.put("msg", msg);
        return result;
    }

}
