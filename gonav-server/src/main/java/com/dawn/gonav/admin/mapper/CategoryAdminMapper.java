package com.dawn.gonav.admin.mapper;

import com.dawn.gonav.model.vo.CategoryPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryAdminMapper {
    List<CategoryPageVO> findAllPageVO(String orderBy, String searchContent);
}
