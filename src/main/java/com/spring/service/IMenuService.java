package com.spring.service;

import java.util.List;

//import java.util.List;

import com.spring.model.Menu;

public interface IMenuService extends ICRUD<Menu, Integer> {

    List<Menu> getMenusByUsername(String username);
}
