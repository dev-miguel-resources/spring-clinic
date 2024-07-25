/*package com.spring.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.MenuDTO;
import com.spring.model.Menu;
import com.spring.service.IMenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;
    private final IMenuService service;

    @PostMapping("/user")
    public ResponseEntity<List<MenuDTO>> getMenusByUser(@RequestBody String username) {
        List<Menu> menus = service.getMenusByUsername(username);
        List<MenuDTO> menusDTO = menus.stream().map(e -> mapper.map(e, MenuDTO.class)).toList();

        return new ResponseEntity<>(menusDTO, HttpStatus.OK);
    }

}*/
