package com.ruoyi.setmeal.mapper;

import java.util.List;
import com.ruoyi.setmeal.domain.Setmeal;
import com.ruoyi.setmeal.domain.SetmealDish;

/**
 * 套餐Mapper接口
 * 
 * @author SuRL
 * @date 2024-09-18
 */
public interface SetmealMapper 
{
    /**
     * 查询套餐
     * 
     * @param id 套餐主键
     * @return 套餐
     */
    public Setmeal selectSetmealById(Long id);

    /**
     * 查询套餐列表
     * 
     * @param setmeal 套餐
     * @return 套餐集合
     */
    public List<Setmeal> selectSetmealList(Setmeal setmeal);

    /**
     * 新增套餐
     * 
     * @param setmeal 套餐
     * @return 结果
     */
    public int insertSetmeal(Setmeal setmeal);

    /**
     * 修改套餐
     * 
     * @param setmeal 套餐
     * @return 结果
     */
    public int updateSetmeal(Setmeal setmeal);

    /**
     * 删除套餐
     * 
     * @param id 套餐主键
     * @return 结果
     */
    public int deleteSetmealById(Long id);

    /**
     * 批量删除套餐
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSetmealByIds(Long[] ids);

    /**
     * 批量删除套餐详情
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSetmealDishBySetmealIds(Long[] ids);
    
    /**
     * 批量新增套餐详情
     * 
     * @param setmealDishList 套餐详情列表
     * @return 结果
     */
    public int batchSetmealDish(List<SetmealDish> setmealDishList);
    

    /**
     * 通过套餐主键删除套餐详情信息
     * 
     * @param id 套餐ID
     * @return 结果
     */
    public int deleteSetmealDishBySetmealId(Long id);
}
