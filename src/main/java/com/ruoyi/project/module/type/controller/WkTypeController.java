package com.ruoyi.project.module.type.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.module.type.domain.WkType;
import com.ruoyi.project.module.type.service.IWkTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 分类Controller
 *
 * @author ruoyi
 * @date 2020-11-12
 */
@Controller
@RequestMapping("/module/type")
public class WkTypeController extends BaseController
{
    private String prefix = "module/type";

    @Autowired
    private IWkTypeService wkTypeService;

    @RequiresPermissions("module:type:view")
    @GetMapping()
    public String type()
    {
        return "module/type/type";
    }

    /**
     * 查询分类列表
     */
    @RequiresPermissions("module:type:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WkType wkType)
    {
        startPage();
        List<WkType> list = wkTypeService.selectWkTypeList(wkType);
        return getDataTable(list);
    }

    /**
     * 导出分类列表
     */
    @RequiresPermissions("module:type:export")
    @Log(title = "分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WkType wkType)
    {
        List<WkType> list = wkTypeService.selectWkTypeList(wkType);
        ExcelUtil<WkType> util = new ExcelUtil<WkType>(WkType.class);
        return util.exportExcel(list, "type");
    }

    /**
     * 新增分类
     */
    @GetMapping("/add")
    public String add()
    {
        return "module/type/add";
    }

    /**
     * 新增保存分类
     */
    @RequiresPermissions("module:type:add")
    @Log(title = "分类", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(WkType wkType)
    {
        return toAjax(wkTypeService.insertWkType(wkType));
    }

    /**
     * 修改分类
     */
    @GetMapping("/edit/{typeId}")
    public String edit(@PathVariable("typeId") String typeId, ModelMap mmap)
    {
        WkType wkType = wkTypeService.selectWkTypeById(typeId);
        mmap.put("wkType", wkType);
        return "module/type/edit";
    }

    /**
     * 修改保存分类
     */
    @RequiresPermissions("module:type:edit")
    @Log(title = "分类", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(WkType wkType)
    {
        return toAjax(wkTypeService.updateWkType(wkType));
    }

    /**
     * 删除分类
     */
    @RequiresPermissions("module:type:remove")
    @Log(title = "分类", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(wkTypeService.deleteWkTypeByIds(ids));
    }
}
