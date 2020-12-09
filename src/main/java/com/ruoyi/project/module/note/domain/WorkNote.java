package com.ruoyi.project.module.note.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * 前端展示所需要的字段
 */
@Data
public class WorkNote implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 笔记id
     */
    private String noteId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 需求id
     */
    private String demandId;

    /**
     * CQ单号
     */
    @Excel(name = "CQ单号")
    private String cqNum;

    /**
     * 需求类型id
     */
    private String demandTypeId;

    /**
     * 需求类型
     */
    @Excel(name = "需求类型")
    private String demandType;

    /**
     * 需求内容
     */
    @Excel(name = "需求内容")
    private String demandContent;

    /**
     * 工作大类
     */
    @Excel(name = "工作大类")
    private String workCategory;

    /**
     * 工作小类
     */
    @Excel(name = "工作小类")
    private String workType;

    /**
     * 工作内容
     */
    @Excel(name = "工作内容")
    private String workItem;

    /**
     * 工作时长
     */
    @Excel(name = "工作时长")
    private String workHours;

    /**
     * 输出物名称
     */
    @Excel(name = "附件")
    private String outputName;

    /**
     * 工作起始日期
     */
    @Excel(name = "工作日期")
    private String workDate;

    /**
     * 工作结束日期
     */
    private String workDateEnd;

    /**
     * 工作内容（纯文本）
     */
    private String workDesc;

    /**
     * 工作内容（带标签）
     */
    private String workText;

    /**
     * 是否有输出物，0否，1是
     */
    private String hasOutput;

    /**
     * 输出物地址
     */
    private String outputUrl;

    /**
     * 筛选条件-起始日期
     */
    private String startTime;

    /** 筛选条件-结束日期
     *
     */
    private String endTime;

    /**
     * 展示类型，0我的，1全部
     */
    private int showType;

}
