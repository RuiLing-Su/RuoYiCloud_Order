package com.ruoyi.setmeal.domain;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 套餐对象 tb_setmeal
 * 
 * @author SuRL
 * @date 2024-09-18
 */
public class Setmeal extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 菜品分类id */
    @Excel(name = "菜品分类id")
    private Long categoryId;

    /** 套餐名称 */
    @Excel(name = "套餐名称")
    private String name;

    /** 套餐价格 */
    @Excel(name = "套餐价格")
    private BigDecimal price;

    /** 售卖状态 */
    @Excel(name = "售卖状态")
    private Long status;

    /** 编码 */
    private String code;

    /** 描述信息 */
    private String description;

    /** 图片 */
    @Excel(name = "图片")
    private String image;

    /** 套餐详情信息 */
    private List<SetmealDish> setmealDishList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
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
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setImage(String image) 
    {
        this.image = image;
    }

    public String getImage() 
    {
        return image;
    }

    public List<SetmealDish> getSetmealDishList()
    {
        return setmealDishList;
    }

    public void setSetmealDishList(List<SetmealDish> setmealDishList)
    {
        this.setmealDishList = setmealDishList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("categoryId", getCategoryId())
            .append("name", getName())
            .append("price", getPrice())
            .append("status", getStatus())
            .append("code", getCode())
            .append("description", getDescription())
            .append("image", getImage())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("setmealDishList", getSetmealDishList())
            .toString();
    }
}
