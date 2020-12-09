package com.ruoyi.project.module.type.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.module.type.domain.WkType;
import com.ruoyi.project.module.type.mapper.WkTypeMapper;
import com.ruoyi.project.module.type.service.IWkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 分类Service业务层处理
 *
 * @author ruoyi
 * @date 2020-11-12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WkTypeServiceImpl implements IWkTypeService
{
    @Autowired
    private WkTypeMapper wkTypeMapper;

    /**
     * 查询分类
     *
     * @param typeId 分类ID
     * @return 分类
     */
    @Override
    public WkType selectWkTypeById(String typeId)
    {
        return wkTypeMapper.selectWkTypeById(typeId);
    }

    /**
     * 查询分类列表
     *
     * @param wkType 分类
     * @return 分类
     */
    @Override
    public List<WkType> selectWkTypeList(WkType wkType)
    {
        return wkTypeMapper.selectWkTypeList(wkType);
    }

    /**
     * 新增分类
     *
     * @param wkType 分类
     * @return 结果
     */
    @Override
    public int insertWkType(WkType wkType)
    {
        wkType.setCreateTime(DateUtils.dateTimeNow());
        return wkTypeMapper.insertWkType(wkType);
    }

    /**
     * 修改分类
     *
     * @param wkType 分类
     * @return 结果
     */
    @Override
    public int updateWkType(WkType wkType)
    {
        return wkTypeMapper.updateWkType(wkType);
    }

    /**
     * 删除分类对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWkTypeByIds(String ids)
    {
        return wkTypeMapper.deleteWkTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除分类信息
     *
     * @param typeId 分类ID
     * @return 结果
     */
    @Override
    public int deleteWkTypeById(String typeId)
    {
        return wkTypeMapper.deleteWkTypeById(typeId);
    }
}
