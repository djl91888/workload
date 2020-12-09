package com.ruoyi.project.need.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 需求表实体类
 * 
 * @author cty
 */
@Data
@ApiModel(description="需求表")
public class Need
{
    private static final long serialVersionUID = 1L;

    /** 需求id */
    @ApiModelProperty(value = "需求id")
    private String demandId;

    /** 用户id */
    @ApiModelProperty(value = "用户id")
    private String userId;

    /** CQ单号 */
    @ApiModelProperty(value = "CQ单号")
    @Excel(name = "CQ单号")
    private String cqNum;

    /** 负责人员 角色+用户id **/
    @ApiModelProperty(value = "用户id+角色id(负责人)")
    private  String perSon;
    /** 连接类别表 */
    @ApiModelProperty(value = "需求类型")
    @Excel(name = "需求类型")
    private String demandTypeId;

    /** 类别名称  */
    @ApiModelProperty(value = "需求类型名称")
    private String demandTypeName;

    /** 需求内容 */
    @Excel(name = "需求内容")
    @ApiModelProperty(value = "需求内容")
    private String demandContent;

    /** 0新建，1进行中，2已上线，3关闭 */
    @Excel(name = "0新建，1进行中，2已上线，3关闭")
    @ApiModelProperty(value = "需求状态")
    private String demandStatus;

    /** 需求描述 */
    @Excel(name = "需求描述")
    @ApiModelProperty(value = "需求描述")
    private String demandDesc;

    /** 单位：人/日 */
    @Excel(name = "预计工作量(单位：人/日)")
    @ApiModelProperty(value = "单位：人/日")
    private String expectWorkload;

    /** 连接来源类型表 */
    @Excel(name = "需求来源")
    @ApiModelProperty(value = "来源类型")
    private String sourceType;


    /** 来源类型id */
    @ApiModelProperty(value = "来源类型id")
    private String sourceId;
    /** 需求来源姓名 */
    @Excel(name = "提出人")
    @ApiModelProperty(value = "提出人")
    private String sourceBy;


    /** 需求的记录时间 */
    @Excel(name = "记录时间")
    @ApiModelProperty(value = "记录时间")
    private String demandTime;
    /** 需求的创建开始时间 */
    @ApiModelProperty(value = "需求的创建开始时间")
    private String createStartTime;
    /** 需求的创建结束时间 */
    @ApiModelProperty(value = "需求的创建结束时间")
    private String createEndTime;
    /** 需求的创建时间 */
    @Excel(name = "创建时间")
    @ApiModelProperty(value = "需求的创建时间")
    private String createTime;
    /** 上线时间 */
    @Excel(name = "上线时间")
    @ApiModelProperty(value = "上线时间")
    private String onlineTime;

    /** 创建人id */
    @Excel(name = "创建人id")
    @ApiModelProperty(value = "创建人id")
    private String createrId;

    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    @ApiModelProperty(value = "创建人姓名")
    private String createrName;

    /** 角色id */
    @ApiModelProperty(value = "角色id")
    private String roleId;

    /** 角色名称 */
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    /** 用户名称 */
    @ApiModelProperty(value = "用户名称")
    private String userName;
    /** 其他相关人员id */
    @ApiModelProperty(value = "其他相关人员id")
    private String relevant;

    /** 其他相关人员id */
    @ApiModelProperty(value = "其他相关人员id")
    private String relevant1;

    /**
     * 负责人id
     */
    @ApiModelProperty(value = "负责人员id")
    private String people;

    /**
     * 负责人id
     */
    @ApiModelProperty(value = "负责人员id")
    private String people1;
    @ApiModelProperty(value = "需求可编辑状态")
    /** 需求可编辑状态   */
    private String editStatus;
    /**
     * 需求状态
     */
     private String status;






}
