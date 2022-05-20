package org.noahsark.shardingjdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.noahsark.shardingjdbc.mapper.ConfigMapper;
import org.noahsark.shardingjdbc.mapper.OrderItemMapper;
import org.noahsark.shardingjdbc.mapper.OrderMapper;
import org.noahsark.shardingjdbc.model.Config;
import org.noahsark.shardingjdbc.model.Order;
import org.noahsark.shardingjdbc.model.OrderDetail;
import org.noahsark.shardingjdbc.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private ConfigMapper configMapper;

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

    @Test
    public void getOrderDetailsTest() {

        List<Long> list = Arrays.asList(732635574220881920L, 732635574220881920L);

        List<OrderDetail> orderDetails = orderMapper.getOrderDetails(list);

        for (OrderDetail orderDetail : orderDetails) {
            System.out.println("orderDetail = " + orderDetail);
        }
    }

    @Test
    public void insertConfigTest() {

        Config config = new Config();
        config.setCode("OS");
        config.setName("os");
        config.setCreateDate(new Date());

        configMapper.insert(config);

        System.out.println("config:" + config);

    }

    @Test
    public void getAllTest() {

        List<Order> list = orderMapper.getAll();

        for (Order order : list) {
            System.out.println("order = " + order);
        }
    }

}
