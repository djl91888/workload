package com.ruoyi.project.module.model.service;

import java.util.List;
import com.ruoyi.project.module.model.domain.WkModel;

/**
 * 工作量模型Service接口
 * 
 * @author ruoyi
 * @date 2020-11-12
 */
public interface IWkModelService 
{
    /**
     * 查询工作量模型
     * 
     * @param modelId 工作量模型ID
     * @return 工作量模型
     */
    public WkModel selectWkModelById(String modelId);

    /**
     * 查询工作量模型列表
     * 
     * @param wkModel 工作量模型
     * @return 工作量模型集合
     */
    public List<WkModel> selectWkModelList(WkModel wkModel);

    /**
     * 新增工作量模型
     * 
     * @param wkModel 工作量模型
     * @return 结果
     */
    public int insertWkModel(WkModel wkModel);

    /**
     * 修改工作量模型
     * 
     * @param wkModel 工作量模型
     * @return 结果
     */
    public int updateWkModel(WkModel wkModel);

    /**
     * 批量删除工作量模型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWkModelByIds(String ids);

    /**
     * 删除工作量模型信息
     * 
     * @param modelId 工作量模型ID
     * @return 结果
     */
    public int deleteWkModelById(String modelId);
}
