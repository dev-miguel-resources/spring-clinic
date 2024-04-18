package com.spring.service.impl;

import java.util.List;

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

    @Override
    public List<ConsultExam> getExamsByConsultId(Integer idConsult) {
        return repo.getExamsByConsultId(idConsult);
    }
    
}
