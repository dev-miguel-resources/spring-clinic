package com.spring.service.impl;

//import java.util.List;

//import org.springframework.security.core.context.SecurityContextHolder;
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

    /*@Override
    public List<Menu> getMenusByUsername(String username) {
        String contextSessionUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return repo.getMenusByUsername(contextSessionUser);
    }*/

}
