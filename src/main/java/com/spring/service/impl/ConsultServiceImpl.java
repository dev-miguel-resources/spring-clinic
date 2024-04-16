package com.spring.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.model.Consult;
import com.spring.model.Exam;
import com.spring.repo.IGenericRepo;
import com.spring.repo.IConsultExamRepo;
import com.spring.repo.IConsultRepo;
import com.spring.service.IConsultService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
//@Transactional
public class ConsultServiceImpl extends CRUDImpl<Consult, Integer> implements IConsultService {

    private final IConsultRepo consultRepo;
    private final IConsultExamRepo ceRepo;

    @Override
    protected IGenericRepo<Consult, Integer> getRepo() {
        return consultRepo;
    }

    @Transactional
    @Override
    public Consult saveTransactional(Consult consult, List<Exam> exams) {
        // 1. consulta con su detalle
        consultRepo.save(consult);
        // 2. List Exams
        exams.forEach(ex -> ceRepo.saveExam(consult.getIdConsult(), ex.getIdExam()));

        return consult;
    }

    // definiciones para más adelante
    
}
