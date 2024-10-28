package com.dawn.gonav.admin.service;

import com.dawn.gonav.model.dto.MenuDTO;
import com.dawn.gonav.model.po.Menu;

import java.util.List;

public interface MenuService {
    void addMenu(MenuDTO menuDTO);

    void delMenu(Integer id);

    void updateMenu(MenuDTO menuDTO);

    List<Menu> listMenu(Integer type);
}
