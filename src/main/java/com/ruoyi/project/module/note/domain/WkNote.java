package com.ruoyi.project.module.note.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * wk_note 表中的字段
 *
 * @author ruoyi
 * @date 2020-11-12
 */
@Data
public class WkNote implements Serializable {

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
     * 需求类型id
     */
    private String demandTypeId;

    /**
     * 工作大类id
     */
    private String workCategoryId;

    /**
     * 工作小类id
     */
    private String workTypeId;

    /**
     * 工作量模型id
     */
    private String workModelId;

    /**
     * 工作时长
     */
    private String workHours;

    /**
     * 工作内容（纯文本）
     */
    private String workDesc;

    /**
     * 工作内容（带标签）
     */
    private String workText;

    /**
     * 工作起始时间
     */
    private String workDate;

    /**
     * 工作结束时间
     */
    private String workDateEnd;

    /**
     * 是否有附件
     */
    private String hasOutput;

    /**
     * 附件名称
     */
    private String outputName;

    /**
     * 附件存放路径
     */
    private String outputUrl;

    /**
     * 创建人
     */
    private String createrId;

    /**
     * 创建人姓名
     */
    private String createrName;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 备用1
     */
    private String param1;

    /**
     * 备用2
     */
    private String param2;

}
