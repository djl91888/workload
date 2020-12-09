package com.ruoyi.project.module.note.domain;

import lombok.Data;

@Data
public class UploadRequest {
    private String noteId;
    private String demandType;
    private String demandContent;
    private String workCategory;
    private String workType;
    private String workItem;
    private String workDate;
}
