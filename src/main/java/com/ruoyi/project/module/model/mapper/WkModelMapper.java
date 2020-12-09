package com.ruoyi.project.module.model.mapper;

import java.util.List;
import com.ruoyi.project.module.model.domain.WkModel;

/**
 * 工作量模型Mapper接口
 * 
 * @author ruoyi
 * @date 2020-11-12
 */
public interface WkModelMapper 
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
     * 删除工作量模型
     * 
     * @param modelId 工作量模型ID
     * @return 结果
     */
    public int deleteWkModelById(String modelId);

    /**
     * 批量删除工作量模型
     * 
     * @param modelIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteWkModelByIds(String[] modelIds);
}
