package com.ruoyi.project.need.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 工作量表实体类
 * 
 * @author cty
 */
@Data
public class Work
{

    private static final long serialVersionUID = 1L;

    /** 工作量id */
    @ApiModelProperty(value = "工作量id")
    private String workloadId;

    /** 需求id */
    @Excel(name = "需求id")
    @ApiModelProperty(value = "需求id")
    private String demandId;

    /** 角色id */
    @Excel(name = "角色id")
    private String roleId;

    /** 工作大类id */
    @Excel(name = "工作大类id")
    @ApiModelProperty(value = "工作大类id")
    private String workCategoryId;

    /** 工作小类id */
    @Excel(name = "工作小类id")
    @ApiModelProperty(value = "工作小类id")
    private String workTypeId;

    /** 工作模型id */
    @Excel(name = "工作模型id")
    @ApiModelProperty(value = "工作模型id")
    private String workModelId;

    /** 具体内容 */
    @Excel(name = "具体内容")
    @ApiModelProperty(value = "具体内容")
    private String workContent;

    /** 难度系数 */
    @Excel(name = "难度系数")
    @ApiModelProperty(value = "难度系数")
    private String difficultDegree;

    /** 工作数量 */
    @Excel(name = "工作数量")
    @ApiModelProperty(value = "工作数量")
    private String workNum;

    /** 单位：小时 */
    @Excel(name = "单位：小时")
    @ApiModelProperty(value = "单位：小时")
    private String workTime;

    /** 创建人id */
    @Excel(name = "创建人id")
    @ApiModelProperty(value = "创建人id")
    private String createrId;

    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    @ApiModelProperty(value = "创建人姓名")
    private String createrName;

    /** 创建人时间 */
    @Excel(name = "创建人时间")
    @ApiModelProperty(value = "创建人时间")
    private String createTime;


}
