package com.ruoyi.project.module.workload.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName WorkloadRequestBean
 * @Description TODO
 * @Author chen
 * @Date 2020/11/16 15:46
 **/
@Data
public class WorkloadRequestBean {

    /**是否全部*/
    @ApiModelProperty(value = "是否全部")
    private String isAll;

    /**模型id*/
    @ApiModelProperty(value = "工作项id")
    private String moduleId;

    /**类型id*/
    @ApiModelProperty(value = "类型id")
    private String demandTypeId;

    /**是否输出物*/
    @ApiModelProperty(value = "是否有输出物 0否，1是")
    private String hasOutput;

    /**提出人*/
    @ApiModelProperty(value = "提出人")
    private String sourceBy;

    /**cq单号*/
    @ApiModelProperty(value = "cq单号")
    private String cqCode;

    /**需求内容*/
    @ApiModelProperty(value = "需求内容")
    private String demandContent;

    /**状态*/
    @ApiModelProperty(value = "状态 0新建，1进行中，2已上线，3关闭")
    private String demandStatus;

    /**上线时间 格式：yyyyMM*/
    @ApiModelProperty(value = "上线时间 格式：yyyyMM")
    private String onlineTime;

    /**工作日期 格式：yyyyMM*/
    @ApiModelProperty(value = "工作日期 格式：yyyyMM")
    private String workDate;

    /**用户id */
    @ApiModelProperty(value = "用户id")
    private String userId;

    /**角色id */
    @ApiModelProperty(value = "角色id")
    private String roleId;

    /**工作大类Id */
    @ApiModelProperty(value = "工作大类Id")
    private String bigTypeId;

    /**工作小类Id */
    @ApiModelProperty(value = "工作小类Id")
    private String smallTypeId;
}
