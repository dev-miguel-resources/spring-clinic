package com.spring.service.impl;

import org.springframework.stereotype.Service;

import com.spring.model.ConsultExam;
import com.spring.repo.IGenericRepo;
import com.spring.repo.IConsultExamRepo;
import com.spring.service.IConsultExamService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultExamServiceImpl extends CRUDImpl<ConsultExam, Integer> implements IConsultExamService {

    private final IConsultExamRepo repo;

    @Override
    protected IGenericRepo<ConsultExam, Integer> getRepo() {
        return repo;
    }

    // definiciones para más adelante
    
}
