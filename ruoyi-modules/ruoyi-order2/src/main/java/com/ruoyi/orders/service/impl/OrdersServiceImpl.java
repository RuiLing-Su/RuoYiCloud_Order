package com.ruoyi.orders.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.orders.domain.OrderDetail;
import com.ruoyi.orders.mapper.OrdersMapper;
import com.ruoyi.orders.domain.Orders;
import com.ruoyi.orders.service.IOrdersService;

/**
 * 用户订单Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-09-18
 */
@Service
public class OrdersServiceImpl implements IOrdersService 
{
    @Autowired
    private OrdersMapper ordersMapper;

    /**
     * 查询用户订单
     * 
     * @param id 用户订单主键
     * @return 用户订单
     */
    @Override
    public Orders selectOrdersById(Long id)
    {
        return ordersMapper.selectOrdersById(id);
    }

    /**
     * 查询用户订单列表
     * 
     * @param orders 用户订单
     * @return 用户订单
     */
    @Override
    public List<Orders> selectOrdersList(Orders orders)
    {
        return ordersMapper.selectOrdersList(orders);
    }

    /**
     * 新增用户订单
     * 
     * @param orders 用户订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertOrders(Orders orders)
    {
        int rows = ordersMapper.insertOrders(orders);
        insertOrderDetail(orders);
        return rows;
    }

    /**
     * 修改用户订单
     * 
     * @param orders 用户订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateOrders(Orders orders)
    {
        ordersMapper.deleteOrderDetailByOrderId(orders.getId());
        insertOrderDetail(orders);
        return ordersMapper.updateOrders(orders);
    }

    /**
     * 批量删除用户订单
     * 
     * @param ids 需要删除的用户订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteOrdersByIds(Long[] ids)
    {
        ordersMapper.deleteOrderDetailByOrderIds(ids);
        return ordersMapper.deleteOrdersByIds(ids);
    }

    /**
     * 删除用户订单信息
     * 
     * @param id 用户订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteOrdersById(Long id)
    {
        ordersMapper.deleteOrderDetailByOrderId(id);
        return ordersMapper.deleteOrdersById(id);
    }

    /**
     * 新增订单明细信息
     * 
     * @param orders 用户订单对象
     */
    public void insertOrderDetail(Orders orders)
    {
        List<OrderDetail> orderDetailList = orders.getOrderDetailList();
        Long id = orders.getId();
        if (StringUtils.isNotNull(orderDetailList))
        {
            List<OrderDetail> list = new ArrayList<OrderDetail>();
            for (OrderDetail orderDetail : orderDetailList)
            {
                orderDetail.setOrderId(id);
                list.add(orderDetail);
            }
            if (list.size() > 0)
            {
                ordersMapper.batchOrderDetail(list);
            }
        }
    }
}
