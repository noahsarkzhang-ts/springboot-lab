package org.noahsark.shardingjdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.noahsark.shardingjdbc.SpringbootShardingJdbcApp;
import org.noahsark.shardingjdbc.mapper.OrderItemMapper;
import org.noahsark.shardingjdbc.mapper.OrderMapper;
import org.noahsark.shardingjdbc.model.Order;
import org.noahsark.shardingjdbc.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Random;

/**
 * OrderMapper Test
 *
 * @author zhangxt
 * @date 2022/05/11 10:01
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMapperTest {

    private Random random = new Random();

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Test
    public void insertTest() {
        Integer orderNo;
        Integer userId;

        int num = 100;

        Order order = null;
        OrderItem orderItem = null;

        for (int i = 0; i < num; i++) {
            order = new Order();
            orderNo = random.nextInt(10000) + 1000;
            userId = random.nextInt(10000) + 10000;

            order.setName("order-" + orderNo);
            order.setOrderNo("" + orderNo);
            order.setUserId(userId);
            order.setPrice(new BigDecimal("1000.5"));

            orderMapper.insert(order);

            orderItem = new OrderItem();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setOrderNo(order.getOrderNo());
            orderItem.setUserId(order.getUserId());
            orderItem.setItemName("Item" + random.nextInt(10000));
            orderItem.setPrice(new BigDecimal(1000.5));

            orderItemMapper.insert(orderItem);

            System.out.println("orderId:" + order.getOrderId());
            System.out.println("itemId:" + orderItem.getItemId());

        }

        //System.out.println("order:" + order);

    }

}
