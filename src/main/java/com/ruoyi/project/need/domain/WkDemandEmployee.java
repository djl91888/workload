package com.ruoyi.project.need.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 需求负责人实体类
 * 
 * @author cty
 */
@Data
@ApiModel(description="需求负责人表")
public class WkDemandEmployee
{

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String unionId;

    /** 需求id */
    @Excel(name = "需求id")
    private String demandId;

    /** 角色id */
    @Excel(name = "角色id")
    private String roleId;

    /** 用户id */
    @Excel(name = "用户id")
    private String userId;

    /** 其他相关人员用户id */
    @Excel(name = "其他相关人员用户id")
    private String relevant;

    /** 创建人id */
    @Excel(name = "创建人id")
    private String createrId;

    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    private String createrName;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private String createTime;


}
