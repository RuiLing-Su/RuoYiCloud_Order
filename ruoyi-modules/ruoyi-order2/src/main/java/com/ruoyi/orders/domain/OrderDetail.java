package com.ruoyi.orders.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 订单明细对象 tb_order_detail
 * 
 * @author ruoyi
 * @date 2024-09-18
 */
public class OrderDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 名字 */
    @Excel(name = "名字")
    private String name;

    /** 图片 */
    @Excel(name = "图片")
    private String image;

    /** 订单id */
    @Excel(name = "订单id")
    private Long orderId;

    /** 菜品id */
    @Excel(name = "菜品id")
    private Long dishId;

    /** 套餐id */
    @Excel(name = "套餐id")
    private Long setmealId;

    /** 口味 */
    @Excel(name = "口味")
    private String dishFlavor;

    /** 数量 */
    @Excel(name = "数量")
    private Long number;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal amount;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setImage(String image) 
    {
        this.image = image;
    }

    public String getImage() 
    {
        return image;
    }
    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setDishId(Long dishId) 
    {
        this.dishId = dishId;
    }

    public Long getDishId() 
    {
        return dishId;
    }
    public void setSetmealId(Long setmealId) 
    {
        this.setmealId = setmealId;
    }

    public Long getSetmealId() 
    {
        return setmealId;
    }
    public void setDishFlavor(String dishFlavor) 
    {
        this.dishFlavor = dishFlavor;
    }

    public String getDishFlavor() 
    {
        return dishFlavor;
    }
    public void setNumber(Long number) 
    {
        this.number = number;
    }

    public Long getNumber() 
    {
        return number;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("image", getImage())
            .append("orderId", getOrderId())
            .append("dishId", getDishId())
            .append("setmealId", getSetmealId())
            .append("dishFlavor", getDishFlavor())
            .append("number", getNumber())
            .append("amount", getAmount())
            .toString();
    }
}
