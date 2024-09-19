package com.ruoyi.orders.service;

import java.util.List;
import com.ruoyi.orders.domain.Orders;

/**
 * 用户订单Service接口
 * 
 * @author ruoyi
 * @date 2024-09-18
 */
public interface IOrdersService 
{
    /**
     * 查询用户订单
     * 
     * @param id 用户订单主键
     * @return 用户订单
     */
    public Orders selectOrdersById(Long id);

    /**
     * 查询用户订单列表
     * 
     * @param orders 用户订单
     * @return 用户订单集合
     */
    public List<Orders> selectOrdersList(Orders orders);

    /**
     * 新增用户订单
     * 
     * @param orders 用户订单
     * @return 结果
     */
    public int insertOrders(Orders orders);

    /**
     * 修改用户订单
     * 
     * @param orders 用户订单
     * @return 结果
     */
    public int updateOrders(Orders orders);

    /**
     * 批量删除用户订单
     * 
     * @param ids 需要删除的用户订单主键集合
     * @return 结果
     */
    public int deleteOrdersByIds(Long[] ids);

    /**
     * 删除用户订单信息
     * 
     * @param id 用户订单主键
     * @return 结果
     */
    public int deleteOrdersById(Long id);
}
