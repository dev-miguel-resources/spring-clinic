package com.spring.service.impl;

import org.springframework.stereotype.Service;

import com.spring.model.Exam;
import com.spring.repo.IGenericRepo;
import com.spring.repo.IExamRepo;
import com.spring.service.IExamService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl extends CRUDImpl<Exam, Integer> implements IExamService {

    private final IExamRepo repo;

    @Override
    protected IGenericRepo<Exam, Integer> getRepo() {
        return repo;
    }
    
}
