package com.ruoyi.project.need.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 导出需求实体类
 * 
 * @author cty
 */
@Data
@ApiModel(description="导出需求表")
public class Export
{
    private static final long serialVersionUID = 1L;

    /** CQ单号 */
    @ApiModelProperty(value = "CQ单号")
    @Excel(name = "CQ单号")
    private String cqNum;

    /** 连接类别表 */
    @ApiModelProperty(value = "需求类型")
    @Excel(name = "需求类型")
    private String demandType;

    /** 需求内容 */
    @Excel(name = "需求内容")
    @ApiModelProperty(value = "需求内容")
    private String demandContent;

    /** 0新建，1进行中，2已上线，3关闭 */
    @Excel(name = "需求状态")
    @ApiModelProperty(value = "需求状态")
    private String demandStatus;

    /** 连接来源类型表 */
    @Excel(name = "需求来源")
    @ApiModelProperty(value = "来源类型")
    private String sourceType;

    /** 提出人 */
    @Excel(name = "提出人")
    @ApiModelProperty(value = "提出人")
    private String sourceBy;

    /** 需求的创建时间 */
    @Excel(name = "创建时间")
    @ApiModelProperty(value = "需求的创建时间")
    private String createTime;

    /** 需求的创建时间 */
    @Excel(name = "创建人")
    @ApiModelProperty(value = "需求的创建人")
    private String createrName;

    /** 单位：人/日 */
    @Excel(name = "预计工作量")
    @ApiModelProperty(value = "单位：人/日")
    private String expectWorkload;

    /** 单位：人/日 */
    @Excel(name = "上线时间")
    @ApiModelProperty(value = "上线时间")
    private String onlineTime;


}
