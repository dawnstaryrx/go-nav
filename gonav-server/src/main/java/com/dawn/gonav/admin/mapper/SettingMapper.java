package com.dawn.gonav.admin.mapper;

import com.dawn.gonav.model.po.Setting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SettingMapper {
    @Select("select * from setting where `key` = #{key}")
    Setting getByKey(String key);

    @Update("update setting set value_text = #{valueText}, value_varchar = #{valueVarchar} where `key` = #{key}")
    void updateSetting(Setting setting);
}
