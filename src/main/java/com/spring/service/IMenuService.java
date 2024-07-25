package com.spring.service;

//import java.util.List;

import com.spring.model.Menu;

public interface IMenuService extends ICRUD<Menu, Integer> {

    // DRY PRINCIPLE: Don´t repeat yourself!
    //List<Menu> getMenusByUsername(String username);
}
