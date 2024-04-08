package com.spring.service.impl;

import org.springframework.stereotype.Service;

import com.spring.model.Specialty;
import com.spring.repo.IGenericRepo;
import com.spring.repo.ISpecialtyRepo;
import com.spring.service.ISpecialtyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl extends CRUDImpl<Specialty, Integer> implements ISpecialtyService {

    private final ISpecialtyRepo repo;

    @Override
    protected IGenericRepo<Specialty, Integer> getRepo() {
        return repo;
    }
    
}
