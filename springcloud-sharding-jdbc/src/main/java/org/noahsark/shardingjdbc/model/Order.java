package org.noahsark.shardingjdbc.model;

import java.math.BigDecimal;

/**
 * 订单
 *
 * @author zhangxt
 * @date 2022/05/10 20:02
 **/
public class Order {

    private Long orderId;

    private String orderNo;

    private Integer userId;

    private String name;

    private BigDecimal price;

    public Order() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderNo='" + orderNo + '\'' +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
