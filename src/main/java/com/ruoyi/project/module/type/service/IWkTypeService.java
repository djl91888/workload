package com.ruoyi.project.module.type.service;

import java.util.List;
import com.ruoyi.project.module.type.domain.WkType;

/**
 * 分类Service接口
 * 
 * @author ruoyi
 * @date 2020-11-12
 */
public interface IWkTypeService 
{
    /**
     * 查询分类
     * 
     * @param typeId 分类ID
     * @return 分类
     */
    public WkType selectWkTypeById(String typeId);

    /**
     * 查询分类列表
     * 
     * @param wkType 分类
     * @return 分类集合
     */
    public List<WkType> selectWkTypeList(WkType wkType);

    /**
     * 新增分类
     * 
     * @param wkType 分类
     * @return 结果
     */
    public int insertWkType(WkType wkType);

    /**
     * 修改分类
     * 
     * @param wkType 分类
     * @return 结果
     */
    public int updateWkType(WkType wkType);

    /**
     * 批量删除分类
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWkTypeByIds(String ids);

    /**
     * 删除分类信息
     * 
     * @param typeId 分类ID
     * @return 结果
     */
    public int deleteWkTypeById(String typeId);
}
