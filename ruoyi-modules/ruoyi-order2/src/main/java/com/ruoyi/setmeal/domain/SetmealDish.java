package com.ruoyi.setmeal.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 套餐详情对象 tb_setmeal_dish
 * 
 * @author SuRL
 * @date 2024-09-18
 */
public class SetmealDish extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 套餐id  */
    @Excel(name = "套餐id ")
    private Long setmealId;

    /** 菜品id */
    @Excel(name = "菜品id")
    private Long dishId;

    /** 菜品名称 （冗余字段） */
    private String name;

    /** 菜品原价（冗余字段） */
    private BigDecimal price;

    /** 份数 */
    private Long copies;

    /** 排序 */
    private Long sort;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSetmealId(Long setmealId)
    {
        this.setmealId = setmealId;
    }

    public Long getSetmealId()
    {
        return setmealId;
    }
    public void setDishId(Long dishId) 
    {
        this.dishId = dishId;
    }

    public Long getDishId() 
    {
        return dishId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setCopies(Long copies) 
    {
        this.copies = copies;
    }

    public Long getCopies() 
    {
        return copies;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("setmealId", getSetmealId())
            .append("dishId", getDishId())
            .append("name", getName())
            .append("price", getPrice())
            .append("copies", getCopies())
            .append("sort", getSort())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
