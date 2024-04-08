package com.spring.service.impl;

import org.springframework.stereotype.Service;

import com.spring.model.Menu;
import com.spring.repo.IGenericRepo;
import com.spring.repo.IMenuRepo;
import com.spring.service.IMenuService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends CRUDImpl<Menu, Integer> implements IMenuService {

    private final IMenuRepo repo;

    @Override
    protected IGenericRepo<Menu, Integer> getRepo() {
        return repo;
    }

    // definiciones para más adelante
    
}
