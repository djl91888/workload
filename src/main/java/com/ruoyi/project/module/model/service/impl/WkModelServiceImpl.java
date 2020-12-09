package com.ruoyi.project.module.model.service.impl;

import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.module.model.domain.WkModel;
import com.ruoyi.project.module.model.mapper.WkModelMapper;
import com.ruoyi.project.module.model.service.IWkModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工作量模型Service业务层处理
 *
 * @author ruoyi
 * @date 2020-11-12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WkModelServiceImpl implements IWkModelService {

    @Autowired
    private WkModelMapper wkModelMapper;

    /**
     * 查询工作量模型
     *
     * @param modelId 工作量模型ID
     * @return 工作量模型
     */
    @Override
    public WkModel selectWkModelById(String modelId) {
        return wkModelMapper.selectWkModelById(modelId);
    }

    /**
     * 查询工作量模型列表
     *
     * @param wkModel 工作量模型
     * @return 工作量模型
     */
    @Override
    public List<WkModel> selectWkModelList(WkModel wkModel) {
        return wkModelMapper.selectWkModelList(wkModel);
    }

    /**
     * 新增工作量模型
     *
     * @param wkModel 工作量模型
     * @return 结果
     */
    @Override
    public int insertWkModel(WkModel wkModel) {
        return wkModelMapper.insertWkModel(wkModel);
    }

    /**
     * 修改工作量模型
     *
     * @param wkModel 工作量模型
     * @return 结果
     */
    @Override
    public int updateWkModel(WkModel wkModel) {
        return wkModelMapper.updateWkModel(wkModel);
    }

    /**
     * 删除工作量模型对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWkModelByIds(String ids) {
        return wkModelMapper.deleteWkModelByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除工作量模型信息
     *
     * @param modelId 工作量模型ID
     * @return 结果
     */
    @Override
    public int deleteWkModelById(String modelId) {
        return wkModelMapper.deleteWkModelById(modelId);
    }
}
