package org.noahsark.shardingjdbc.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.noahsark.shardingjdbc.model.Config;

import java.util.List;

/**
 * 配置 Mapper
 *
 * @author zhangxt
 * @date 2022/05/10 20:10
 **/
public interface ConfigMapper {

    @Select("SELECT * FROM t_config")
    List<Config> getAll();

    @Select("SELECT * FROM t_config WHERE id = #{id}")
    Config get(Long id);

    @Insert("INSERT INTO t_config(code,name,create_date) VALUES(#{code}, #{name}, #{createDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Config config);

    @Update("UPDATE t_config SET name=#{name} WHERE id =#{id}")
    void update(Config config);

    @Delete("DELETE FROM t_config WHERE id =#{id}")
    void delete(Long id);

}
