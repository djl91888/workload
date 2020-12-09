package com.ruoyi.project.module.model.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

/**
 * 工作量模型对象 wk_model
 *
 * @author ruoyi
 * @date 2020-11-12
 */
@Data
public class WkModel {
    private static final long serialVersionUID = 1L;

    /**
     * 模型id
     */
    private String modelId;

    /**
     * 0运营，1支撑
     */
    @Excel(name = "0运营，1支撑")
    private String modelType;

    /**
     * 编号
     */
    @Excel(name = "编号")
    private String modelCode;

    /**
     * 对应的工作小类，关联类别表
     */
    @Excel(name = "对应的工作小类，关联类别表")
    private String workType;

    /**
     * 角色id
     */
    @Excel(name = "角色id")
    private String roleId;

    private String status;

    /**
     * 工作项
     */
    @Excel(name = "工作项")
    private String workItem;

    /**
     * 工作项描述
     */
    @Excel(name = "工作项描述")
    private String workContent;

    /**
     * 0小时，1项/人
     */
    @Excel(name = "0小时，1项/人")
    private String workTimeType;

    /**
     * 参考工时
     */
    @Excel(name = "参考工时")
    private String workTime;

    /**
     * 参考工时单位
     */
    @Excel(name = "参考工时单位")
    private String workTimeUnit;

    /**
     * 难度下限
     */
    @Excel(name = "难度下限")
    private String difficultFloor;

    /**
     * 难度上限
     */
    @Excel(name = "难度上限")
    private String difficultCeiling;

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
