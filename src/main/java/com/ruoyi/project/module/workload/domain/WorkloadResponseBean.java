package com.ruoyi.project.module.workload.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName 工作量响应实体
 * @Description TODO
 * @Author chen
 * @Date 2020/11/23 11:41
 **/
@Data
public class WorkloadResponseBean {
    /**
     * 需求id
     */
    @ApiModelProperty(value = "需求id")
    private String demandId;

    /**
     * 工作量id
     */
    @ApiModelProperty(value = "工作量id")
    private String workloadId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private String userId;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private String roleId;

    /**
     * 月份
     */
    @Excel(name = "月份")
    @ApiModelProperty(value = "月份")
    private String workDate;

    /**
     * 提出人
     */
    @Excel(name = "提出人")
    @ApiModelProperty(value = "提出人")
    private String sourceBy;

    /**
     * CQ单号
     */
    @Excel(name = "CQ单号")
    @ApiModelProperty(value = "CQ单号")
    private String cqCode;

    /**
     * 需求类型id
     */
    @ApiModelProperty(value = "需求类型id")
    private String demandTypeId;

    /**
     * 需求类型名称
     */
    @Excel(name = "需求类型")
    @ApiModelProperty(value = "需求类型名称")
    private String demandName;

    /**
     * 需求内容
     */
    @Excel(name = "需求内容")
    @ApiModelProperty(value = "需求内容/需求名称")
    private String demandContent;

    /**
     * 工作大类id
     */
    @ApiModelProperty(value = "工作大类id")
    private String bigTypeId;

    /**
     * 工作大类
     */
    @Excel(name = "工作大类")
    @ApiModelProperty(value = "工作大类")
    private String workBigType;

    /**
     * 工作小类id
     */
    @ApiModelProperty(value = "工作小类id")
    private String smallTypeId;

    /**
     * 工作小类
     */
    @Excel(name = "工作小类")
    @ApiModelProperty(value = "工作小类")
    private String workSmallType;

    /**
     * 工作内容
     */
    @ApiModelProperty(value = "工作内容")
    private String workContent;

    /**
     * 工作内容
     */
    @Excel(name = "工作内容")
    @ApiModelProperty(value = "工作内容文本")
    private String workText;

    /**
     * 输出物名称
     */
    @Excel(name = "输出物")
    @ApiModelProperty(value = "输出物名称")
    private String outputName;

    /**
     * 输出物地址
     */
    @ApiModelProperty(value = "输出物地址")
    private String outputUrl;

    /**
     * 模型编号
     */
    @Excel(name = "模型编号")
    @ApiModelProperty(value = "模型编号")
    private String modelCode;

    /**
     * 模型id
     */
    @ApiModelProperty(value = "模型id")
    private String moduleId;

    /**
     * 标准工时
     */
    @Excel(name = "标准工时")
    @ApiModelProperty(value = "标准工时")
    private String normalHours;

    /**
     * 难度系数
     */
    @Excel(name = "难度系数")
    @ApiModelProperty(value = "难度系数")
    private String difficultDegree;

    /**
     * 难度下限
     */
    @ApiModelProperty(value = "难度下限")
    private String difficultFloor;

    /**
     * 难度上限
     */
    @ApiModelProperty(value = "难度上限")
    private String difficultCeiling;

    /**
     * 数量
     */
    @Excel(name = "数量")
    @ApiModelProperty(value = "数量")
    private String workNum;

    /**
     * 工时
     */
    @Excel(name = "工时")
    @ApiModelProperty(value = "工时")
    private String workTime;

    /**
     * 工时单位
     */
    @ApiModelProperty(value = "工时单位")
    private String unit;

    /**
     * 人月
     */
    @Excel(name = "人月")
    @ApiModelProperty(value = "人月")
    private String manMonth;

    /**
     * 需求状态
     */
    @ApiModelProperty(value = "需求状态 0新建，1进行中，2已上线，3关闭")
    private String demandStatus;

    /**
     * 需求来源类型名称
     */
    @ApiModelProperty(value = "需求来源类型名称")
    private String demandSource;

    /**
     * 难度系数类型
     */
    @ApiModelProperty(value = "难度系数类型 ：0/小时 1/人")
    private String workTimeType;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createrName;


    /**
     * 工作项
     */
    @ApiModelProperty(value = "工作项")
    private String workItem;

    /**
     * 工作时长
     */
    @ApiModelProperty(value = "工作时长")
    private String workHours;

    /**
     * 预计功能点
     */
    @ApiModelProperty(value = "预计功能点")
    private String expectWorkload;

}
