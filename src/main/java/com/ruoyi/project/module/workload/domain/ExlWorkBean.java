package com.ruoyi.project.module.workload.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;

/**
 * @ClassName ExlWorkBean
 * @Description TODO
 * @Author chen
 * @Date 2020/11/17 17:24
 **/
public class ExlWorkBean {
    private static final long serialVersionUID = 1L;


    /**
     * 月份
     */
    @Excel(name = "月份")
    private String demandTime;

    /**
     * cq单号
     */
    @Excel(name = "cq单号")
    private String cqCode;

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
     *工作大类
     */
    @Excel(name = "工作大类")
    private String workBigType;

    /**
     * 工作小类
     */
    @Excel(name = "工作小类")
    private String workSmallType;

    /**
     * 输出物是否具备 0不涉及 1是
     */
    @Excel(name = "输出物是否具备")
    private String hasOutput;

    /**
     * 具体内容
     */
    @Excel(name = "工作内容")
    private String workContent;

    /**
     * 输出物名称
     */
    @Excel(name = "输出物名称")
    private String outputName;

    /**
     * 编号
     */
    @Excel(name = "编号")
    private String modelCode;

    /**
     * 标准工时
     */
    @Excel(name = "标准工时")
    private String normalHours;

    /**
     * 难度系数
     */
    @Excel(name = "难度系数")
    private String difficultDegree;

    /**
     * 工时人/月
     */
    @Excel(name = "工时人/月")
    private String manHour;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private String workNum;

    /**
     * 工时
     */
    @Excel(name = "工时")
    private String workTime;

    public String getDemandType() {
        return demandType;
    }

    public void setDemandType(String demandType) {
        this.demandType = demandType;
    }

    public String getDemandContent() {
        return demandContent;
    }

    public void setDemandContent(String demandContent) {
        this.demandContent = demandContent;
    }

    public String getWorkBigType() {
        return workBigType;
    }

    public void setWorkBigType(String workBigType) {
        this.workBigType = workBigType;
    }

    public String getWorkSmallType() {
        return workSmallType;
    }

    public void setWorkSmallType(String workSmallType) {
        this.workSmallType = workSmallType;
    }

    public String getHasOutput() {
        return hasOutput;
    }

    public void setHasOutput(String hasOutput) {
        this.hasOutput = hasOutput;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getNormalHours() {
        return normalHours;
    }

    public void setNormalHours(String normalHours) {
        this.normalHours = normalHours;
    }

    public String getDifficultDegree() {
        return difficultDegree;
    }

    public void setDifficultDegree(String difficultDegree) {
        this.difficultDegree = difficultDegree;
    }

    public String getManHour() {
        return manHour;
    }

    public void setManHour(String manHour) {
        this.manHour = manHour;
    }

    public String getWorkNum() {
        return workNum;
    }

    public void setWorkNum(String workNum) {
        this.workNum = workNum;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }
}
