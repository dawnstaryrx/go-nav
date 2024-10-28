package com.dawn.gonav.admin.mapper;

import com.dawn.gonav.model.po.Menu;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuMapper {
    @Insert("insert into menu(title, url, type, weight, create_time) values(#{title}, #{url}, #{type}, #{weight}, #{createTime})")
    void addMenu(Menu menu);

    @Delete("delete from menu where id = #{id}")
    void delMenuById(Integer id);

    @Update("update menu set title = #{title}, url = #{url}, type = #{type}, weight = #{weight} where id = #{id}")
    void updateMenu(Menu menu);

    @Select("select * from menu where id = #{id}")
    Menu getById(Integer id);

    @Select("select * from menu where type = #{type} order by weight desc")
    List<Menu> getByType(Integer type);
}
