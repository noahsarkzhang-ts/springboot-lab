package org.noahsark.shardingjdbc.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.noahsark.shardingjdbc.model.OrderItem;

import java.util.List;

/**
 * 订单项 Mapper
 *
 * @author zhangxt
 * @date 2022/05/10 20:09
 **/
public interface OrderItemMapper {

    @Select("SELECT * FROM t_order_item")
    List<OrderItem> getAll();

    @Select("SELECT * FROM t_order_item WHERE item_id = #{itemId}")
    OrderItem get(Long itemId);

    @Insert("INSERT INTO t_order_item(order_id,order_no,user_id,item_name,price) VALUES(#{orderId}, #{orderNo},#{userId}, #{itemName}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "itemId")
    void insert(OrderItem orderItem);

    @Update("UPDATE t_order_item SET item_name=#{itemName},price=#{price} WHERE item_id =#{itemId}")
    void update(OrderItem orderItem);

    @Delete("DELETE FROM t_order_item WHERE item_id =#{itemId}")
    void delete(Long itemId);
}
