package com.ruoyi.project.module.workload.domain;

import lombok.Data;

/**
 * 工作量对象 wk_workload
 *
 * @author ruoyi
 * @date 2020-11-13
 */
@Data
public class WkWorkload {

    private static final long serialVersionUID = 1L;

    private String workloadId;

    private String noteId;

    private String demandId;

    private String workCategoryId;

    private String workTypeId;

    private String workModelId;

    private String workContent;

    private String difficultDegree;

    private String workNum;

    private String workTime;

    private String workDate;

    private String hasOutput;

    private String outputName;

    private String outputUrl;

    private String createrId;

    private String createrName;

    private String createTime;

    private String param1;

    private String param2;
}
