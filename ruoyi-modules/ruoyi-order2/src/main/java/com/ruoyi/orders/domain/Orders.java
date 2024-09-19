package com.ruoyi.orders.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 用户订单对象 tb_orders
 * 
 * @author ruoyi
 * @date 2024-09-18
 */
public class Orders extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 桌号 */
    @Excel(name = "桌号")
    private Long tableNo;

    /** 主键 */
    private Long id;

    /** 订单号 */
    @Excel(name = "订单号")
    private String number;

    /** 订单状态 */
    @Excel(name = "订单状态")
    private Long status;

    /** 下单用户 */
    private Long userId;

    /** 下单时间 */
    private Date orderTime;

    /** 结账时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结账时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkoutTime;

    /** 支付方式 1微信,2支付宝 */
    private Long payMethod;

    /** 实收金额 */
    @Excel(name = "实收金额")
    private BigDecimal amount;

    /** 用户手机号 */
    @Excel(name = "用户手机号")
    private String phone;

    /** 订单明细信息 */
    private List<OrderDetail> orderDetailList;

    public void setTableNo(Long tableNo) 
    {
        this.tableNo = tableNo;
    }

    public Long getTableNo() 
    {
        return tableNo;
    }
    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setNumber(String number) 
    {
        this.number = number;
    }

    public String getNumber() 
    {
        return number;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setOrderTime(Date orderTime) 
    {
        this.orderTime = orderTime;
    }

    public Date getOrderTime() 
    {
        return orderTime;
    }
    public void setCheckoutTime(Date checkoutTime) 
    {
        this.checkoutTime = checkoutTime;
    }

    public Date getCheckoutTime() 
    {
        return checkoutTime;
    }
    public void setPayMethod(Long payMethod) 
    {
        this.payMethod = payMethod;
    }

    public Long getPayMethod() 
    {
        return payMethod;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    public List<OrderDetail> getOrderDetailList()
    {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList)
    {
        this.orderDetailList = orderDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tableNo", getTableNo())
            .append("id", getId())
            .append("number", getNumber())
            .append("status", getStatus())
            .append("userId", getUserId())
            .append("orderTime", getOrderTime())
            .append("checkoutTime", getCheckoutTime())
            .append("payMethod", getPayMethod())
            .append("amount", getAmount())
            .append("remark", getRemark())
            .append("phone", getPhone())
            .append("orderDetailList", getOrderDetailList())
            .toString();
    }
}
