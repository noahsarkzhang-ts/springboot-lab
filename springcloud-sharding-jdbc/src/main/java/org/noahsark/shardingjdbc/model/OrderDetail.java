package org.noahsark.shardingjdbc.model;

import java.math.BigDecimal;

/**
 * Order 详情
 *
 * @author zhangxt
 * @date 2022/05/16 10:25
 **/
public class OrderDetail {

    private Long orderId;

    private String orderNo;

    private Integer userId;

    private String name;

    private BigDecimal price;

    private Long itemId;

    public OrderDetail() {
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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId=" + orderId +
                ", orderNo='" + orderNo + '\'' +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", itemId=" + itemId +
                '}';
    }
}
