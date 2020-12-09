package com.ruoyi.project.module.note.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.module.model.domain.WkModel;
import com.ruoyi.project.module.note.domain.UploadRequest;
import com.ruoyi.project.module.note.domain.WkNote;
import com.ruoyi.project.module.note.domain.WorkNote;
import com.ruoyi.project.module.note.service.IWkNoteService;
import com.ruoyi.project.module.type.domain.WkType;
import com.ruoyi.project.module.type.service.IWkTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 笔记Controller
 *
 * @author yuxin
 * @date 2020-11-12
 */
@Controller
@RequestMapping("/note")
public class WkNoteController extends BaseController {

    @Autowired
    private IWkNoteService noteService;

    @Autowired
    private IWkTypeService typeService;

    /**
     * 跳转笔记列表页面
     */
    @GetMapping("/worknote")
    public String worknote() {
        return "workload/worknote";
    }

    /**
     * 从需求列表页面跳转至新建笔记页面
     */
    @GetMapping("/editworknote")
    public String edit(String demandTypeId, String demandId, ModelMap modelMap) {
        modelMap.put("demandTypeId", demandTypeId);
        modelMap.put("demandId", demandId);
        return "workload/editworknote";
    }

    /**
     * 跳转新建/修改/查看笔记页面
     */
    @GetMapping("/editworknote/{noteId}")
    public String edit(@PathVariable("noteId") String noteId, ModelMap modelMap) {
        modelMap.put("noteId", noteId);
        return "workload/editworknote";
    }

    /**
     * 查询是否是主管接口
     */
    @PostMapping("/isSupervisor")
    @ApiOperation(value = "111", notes = "222")
    @ResponseBody
    public AjaxResult isSupervisor() {
        boolean isSupervisor = noteService.isSupervisor();
        return AjaxResult.success(isSupervisor);
    }

    /**
     * 列表页面 - 查询需求类型列表接口
     */
    @PostMapping("/getDemandTypeList")
    @ApiOperation(value = "111", notes = "222")
    @ResponseBody
    public AjaxResult getDemandTypeList() {
        WkType wkType = new WkType();
        wkType.setTypeLevel("1");
        List<WkType> wkTypes = typeService.selectWkTypeList(wkType);
        return AjaxResult.success(wkTypes);
    }

    /**
     * 新建页面 - 查询需求类型列表接口
     */
    @PostMapping("/getDemandTypeListRelevantMe")
    @ApiOperation(value = "111", notes = "222")
    @ResponseBody
    public AjaxResult getDemandTypeListRelevantMe() {
        List<Map<String, String>> typeList = noteService.getDemandTypeList();
        return AjaxResult.success(typeList);
    }

    /**
     * 查询需求列表接口
     */
    @PostMapping("/getDemandList")
    @ApiOperation(value = "111", notes = "222")
    @ResponseBody
    public AjaxResult getDemandList(String demandTypeId) {
        List<Map<String, String>> demandList = noteService.getDemandList(demandTypeId);
        return AjaxResult.success(demandList);
    }

    /**
     * 查询工作大类列表接口
     */
    @PostMapping("/getWorkCategories")
    @ApiOperation(value = "111", notes = "222")
    @ResponseBody
    public AjaxResult getWorkCategories() {
        List<WkType> wkTypes = noteService.getWorkCategories();
        return AjaxResult.success(wkTypes);
    }

    /**
     * 查询工作小类列表接口
     */
    @PostMapping("/getWorkTypes/{secLevel}")
    @ApiOperation(value = "111", notes = "222")
    @ResponseBody
    public AjaxResult getWorkTypes(@PathVariable("secLevel") String secLevel) {
        List<WkType> wkTypes = noteService.getWorkTypes(secLevel);
        return AjaxResult.success(wkTypes);
    }

    /**
     * 查询工作项列表接口
     */
    @PostMapping("/getWorkItems/{workTypeId}")
    @ApiOperation(value = "111", notes = "222")
    @ResponseBody
    public AjaxResult getWorkItems(@PathVariable("workTypeId") String workTypeId) {
        List<WkModel> wkModels = noteService.getWorkItems(workTypeId);
        return AjaxResult.success(wkModels);
    }

    /**
     * 查询笔记列表接口
     */
    @PostMapping("/list")
    @ApiOperation(value = "111", notes = "222")
    @ResponseBody
    public TableDataInfo list(WorkNote workNote) {
        startPage();
        List<WorkNote> workNotes = noteService.getWorkNoteList(workNote);
        return getDataTable(workNotes);
    }

    /**
     * 上传文件接口
     */
    @Log(title = "笔记", businessType = BusinessType.INSERT)
    @PostMapping("/upload")
    @ApiOperation(value = "111", notes = "222")
    @ResponseBody
    public AjaxResult upload(UploadRequest uploadRequest, MultipartFile[] files) {
        WkNote wkNote = noteService.upload(uploadRequest, files);
        String hasOutput = wkNote.getHasOutput();
        if ("1".equals(hasOutput)) return AjaxResult.success("上传成功", wkNote);
        return AjaxResult.error(wkNote.getParam1(), wkNote);
    }

    /**
     * 新增笔记接口
     */
    @Log(title = "笔记", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation(value = "111", notes = "222")
    @ResponseBody
    public AjaxResult addSave(WkNote wkNote) {
        Map<String, String> result = noteService.addWkNote(wkNote);
        String status = result.get("status");
        if ("0".equals(status)) return success();
        String msg = result.get("msg");
        return error(msg);
    }

    /**
     * 修改笔记接口
     */
    @Log(title = "笔记", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ApiOperation(value = "111", notes = "222")
    @ResponseBody
    public AjaxResult editSave(WkNote wkNote) {
        Map<String, String> result = noteService.changeWkNote(wkNote);
        Object status = result.get("status");
        if ("0".equals(status)) return success();
        String msg = result.get("msg");
        return error(msg);
    }

    /**
     * 查看笔记详情接口
     */
    @PostMapping("/detail")
    @ApiOperation(value = "111", notes = "222")
    @ResponseBody
    public AjaxResult showDetail(String noteId) {
        WkNote wkNote = noteService.getWorkNoteById(noteId);
        return AjaxResult.success(wkNote);
    }

    /**
     * 删除笔记接口
     */
    @Log(title = "笔记", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ApiOperation(value = "111", notes = "222")
    @ResponseBody
    public AjaxResult remove(String noteId) {
        Map<String, String> result = noteService.removeWkNoteById(noteId);
        Object status = result.get("status");
        if ("0".equals(status)) return success();
        String msg = result.get("msg");
        return error(msg);
    }

    /**
     * 导出笔记列表接口
     */
    @Log(title = "笔记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "111", notes = "222")
    @ResponseBody
    public AjaxResult export(WorkNote workNote) {
        List<WorkNote> list = noteService.getWorkNoteList(workNote);
        ExcelUtil<WorkNote> util = new ExcelUtil<>(WorkNote.class);
        return util.exportExcel(list, "笔记清单");
    }

}
