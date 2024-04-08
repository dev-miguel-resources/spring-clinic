package com.spring.service.impl;

import org.springframework.stereotype.Service;

import com.spring.model.Medic;
import com.spring.repo.IGenericRepo;
import com.spring.repo.IMedicRepo;
import com.spring.service.IMedicService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicServiceImpl extends CRUDImpl<Medic, Integer> implements IMedicService {

    private final IMedicRepo repo;

    @Override
    protected IGenericRepo<Medic, Integer> getRepo() {
        return repo;
    }
    
}
