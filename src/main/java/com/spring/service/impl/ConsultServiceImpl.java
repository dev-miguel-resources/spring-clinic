package com.spring.service.impl;

import org.springframework.stereotype.Service;

import com.spring.model.Consult;
import com.spring.repo.IGenericRepo;
import com.spring.repo.IConsultRepo;
import com.spring.service.IConsultService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultServiceImpl extends CRUDImpl<Consult, Integer> implements IConsultService {

    private final IConsultRepo repo;

    @Override
    protected IGenericRepo<Consult, Integer> getRepo() {
        return repo;
    }

    // definiciones para más adelante
    
}
