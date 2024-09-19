package com.ruoyi.setmeal.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.setmeal.domain.SetmealDish;
import com.ruoyi.setmeal.mapper.SetmealMapper;
import com.ruoyi.setmeal.domain.Setmeal;
import com.ruoyi.setmeal.service.ISetmealService;

/**
 * 套餐Service业务层处理
 * 
 * @author SuRL
 * @date 2024-09-18
 */
@Service
public class SetmealServiceImpl implements ISetmealService 
{
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 查询套餐
     * 
     * @param id 套餐主键
     * @return 套餐
     */
    @Override
    public Setmeal selectSetmealById(Long id)
    {
        return setmealMapper.selectSetmealById(id);
    }

    /**
     * 查询套餐列表
     * 
     * @param setmeal 套餐
     * @return 套餐
     */
    @Override
    public List<Setmeal> selectSetmealList(Setmeal setmeal)
    {
        return setmealMapper.selectSetmealList(setmeal);
    }

    /**
     * 新增套餐
     * 
     * @param setmeal 套餐
     * @return 结果
     */
    @Transactional
    @Override
    public int insertSetmeal(Setmeal setmeal)
    {
        setmeal.setCreateTime(DateUtils.getNowDate());
        int rows = setmealMapper.insertSetmeal(setmeal);
        insertSetmealDish(setmeal);
        return rows;
    }

    /**
     * 修改套餐
     * 
     * @param setmeal 套餐
     * @return 结果
     */
    @Transactional
    @Override
    public int updateSetmeal(Setmeal setmeal)
    {
        setmeal.setUpdateTime(DateUtils.getNowDate());
        setmealMapper.deleteSetmealDishBySetmealId(setmeal.getId());
        insertSetmealDish(setmeal);
        return setmealMapper.updateSetmeal(setmeal);
    }

    /**
     * 批量删除套餐
     * 
     * @param ids 需要删除的套餐主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSetmealByIds(Long[] ids)
    {
        setmealMapper.deleteSetmealDishBySetmealIds(ids);
        return setmealMapper.deleteSetmealByIds(ids);
    }

    /**
     * 删除套餐信息
     * 
     * @param id 套餐主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSetmealById(Long id)
    {
        setmealMapper.deleteSetmealDishBySetmealId(id);
        return setmealMapper.deleteSetmealById(id);
    }

    /**
     * 新增套餐详情信息
     * 
     * @param setmeal 套餐对象
     */
    public void insertSetmealDish(Setmeal setmeal)
    {
        List<SetmealDish> setmealDishList = setmeal.getSetmealDishList();
        Long id = setmeal.getId();
        if (StringUtils.isNotNull(setmealDishList))
        {
            List<SetmealDish> list = new ArrayList<SetmealDish>();
            for (SetmealDish setmealDish : setmealDishList)
            {
                setmealDish.setSetmealId(id);
                list.add(setmealDish);
            }
            if (list.size() > 0)
            {
                setmealMapper.batchSetmealDish(list);
            }
        }
    }
}
