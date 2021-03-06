package org.noahsark.shardingjdbc.model;

import java.math.BigDecimal;

/**
 * 订单项
 *
 * @author zhangxt
 * @date 2022/05/10 20:05
 **/
public class OrderItem {

    private Long itemId;

    private Long orderId;

    private String orderNo;

    private Integer userId;

    private String itemName;

    private BigDecimal price;

    private OrderItem item;

    public OrderItem() {
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OrderItem getItem() {
        return item;
    }

    public void setItem(OrderItem item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemId=" + itemId +
                ", orderId=" + orderId +
                ", orderNo='" + orderNo + '\'' +
                ", userId=" + userId +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", item=" + item +
                '}';
    }
}
