package com.ruoyi.category.service;

import java.util.List;
import com.ruoyi.category.domain.Category;

/**
 * 菜品及套餐分类Service接口
 * 
 * @author SuRL
 * @date 2024-09-18
 */
public interface ICategoryService 
{
    /**
     * 查询菜品及套餐分类
     * 
     * @param id 菜品及套餐分类主键
     * @return 菜品及套餐分类
     */
    public Category selectCategoryById(Long id);

    /**
     * 查询菜品及套餐分类列表
     * 
     * @param category 菜品及套餐分类
     * @return 菜品及套餐分类集合
     */
    public List<Category> selectCategoryList(Category category);

    /**
     * 新增菜品及套餐分类
     * 
     * @param category 菜品及套餐分类
     * @return 结果
     */
    public int insertCategory(Category category);

    /**
     * 修改菜品及套餐分类
     * 
     * @param category 菜品及套餐分类
     * @return 结果
     */
    public int updateCategory(Category category);

    /**
     * 批量删除菜品及套餐分类
     * 
     * @param ids 需要删除的菜品及套餐分类主键集合
     * @return 结果
     */
    public int deleteCategoryByIds(Long[] ids);

    /**
     * 删除菜品及套餐分类信息
     * 
     * @param id 菜品及套餐分类主键
     * @return 结果
     */
    public int deleteCategoryById(Long id);
}
