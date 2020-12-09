package com.ruoyi.project.module.model.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.module.model.domain.WkModel;
import com.ruoyi.project.module.model.service.IWkModelService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 工作量模型Controller
 *
 * @author ruoyi
 * @date 2020-11-12
 */
@Controller
@RequestMapping("/module/model")
public class WkModelController extends BaseController
{
    private String prefix = "module/model";

    @Autowired
    private IWkModelService wkModelService;

    @RequiresPermissions("module:model:view")
    @GetMapping()
    public String model()
    {
        return "module/model/model";
    }

    /**
     * 查询工作量模型列表
     */
    @RequiresPermissions("module:model:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WkModel wkModel)
    {
        startPage();
        List<WkModel> list = wkModelService.selectWkModelList(wkModel);
        return getDataTable(list);
    }

    /**
     * 导出工作量模型列表
     */
    @RequiresPermissions("module:model:export")
    @Log(title = "工作量模型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WkModel wkModel)
    {
        List<WkModel> list = wkModelService.selectWkModelList(wkModel);
        ExcelUtil<WkModel> util = new ExcelUtil<WkModel>(WkModel.class);
        return util.exportExcel(list, "model");
    }

    /**
     * 新增工作量模型
     */
    @GetMapping("/add")
    public String add()
    {
        return "module/model/add";
    }

    /**
     * 新增保存工作量模型
     */
    @RequiresPermissions("module:model:add")
    @Log(title = "工作量模型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(WkModel wkModel)
    {
        return toAjax(wkModelService.insertWkModel(wkModel));
    }

    /**
     * 修改工作量模型
     */
    @GetMapping("/edit/{modelId}")
    public String edit(@PathVariable("modelId") String modelId, ModelMap mmap)
    {
        WkModel wkModel = wkModelService.selectWkModelById(modelId);
        mmap.put("wkModel", wkModel);
        return "module/model/edit";
    }

    /**
     * 修改保存工作量模型
     */
    @RequiresPermissions("module:model:edit")
    @Log(title = "工作量模型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(WkModel wkModel)
    {
        return toAjax(wkModelService.updateWkModel(wkModel));
    }

    /**
     * 删除工作量模型
     */
    @RequiresPermissions("module:model:remove")
    @Log(title = "工作量模型", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(wkModelService.deleteWkModelByIds(ids));
    }
}
