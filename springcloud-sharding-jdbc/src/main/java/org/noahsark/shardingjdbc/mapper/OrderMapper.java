package org.noahsark.shardingjdbc.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.noahsark.shardingjdbc.model.Order;

import java.util.List;

/**
 * 订单 Mapper
 *
 * @author zhangxt
 * @date 2022/05/10 20:08
 **/
public interface OrderMapper {

    @Select("SELECT * FROM t_order")
    List<Order> getAll();

    @Select("SELECT * FROM t_order WHERE order_id = #{orderId}")
    Order get(Long orderId);

    @Insert("INSERT INTO t_order(order_no,name,price) VALUES(#{orderNo}, #{name}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    void insert(Order order);

    @Update("UPDATE t_order SET name=#{name},price=#{price} WHERE order_id =#{orderId}")
    void update(Order order);

    @Delete("DELETE FROM t_order WHERE order_id =#{orderId}")
    void delete(Long orderId);
}
