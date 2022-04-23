package org.noahsark.cache.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.noahsark.cache.model.User;

import java.util.List;

/**
 * User Mapper
 *
 * @author zhangxt
 * @date 2022/04/19 10:19
 **/
public interface UserMapper {

    @Select("SELECT * FROM t_user_info")
    List<User> getAll();

    @Select("SELECT * FROM t_user_info WHERE id = #{id}")
    User getUser(Integer id);

    @Insert("INSERT INTO t_user_info(name,password,sex,nickname) VALUES(#{name}, #{password}, #{sex}, #{nickname})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Update("UPDATE t_user_info SET name=#{name},nickname=#{nickname} WHERE id =#{id}")
    void update(User user);

    @Delete("DELETE FROM t_user_info WHERE id =#{id}")
    void delete(Integer id);

}
