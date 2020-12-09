package com.ruoyi.project.module.type.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分类对象 wk_type
 *
 * @author ruoyi
 * @date 2020-11-12
 */
@Data
@Accessors(chain = true)
public class WkType {

    private static final long serialVersionUID = 1L;

    /**
     * 类别id
     */
    private String typeId;

    /**
     * 1需求类型，2工作大类，3工作小类
     */
    @Excel(name = "1需求类型，2工作大类，3工作小类")
    private String typeLevel;

    /**
     * 1运营，2支撑，3基础运营，4基础支撑；只用作“工作小类”
     */
    @Excel(name = "1运营，2支撑，3基础运营，4基础支撑；只用作“工作小类”")
    private String secLevel;

    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String typeName;

    /**
     * 创建人id
     */
    @Excel(name = "创建人id")
    private String createrId;

    /**
     * 创建人姓名
     */
    @Excel(name = "创建人姓名")
    private String createrName;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private String createTime;

    /**
     * 备用1
     */
    @Excel(name = "备用1")
    private String param1;

    /**
     * 备用2
     */
    @Excel(name = "备用2")
    private String param2;

}
