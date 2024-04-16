package com.spring.service;

import java.util.List;

import com.spring.model.Consult;
import com.spring.model.Exam;

public interface IConsultService extends ICRUD<Consult, Integer> {

    // DRY PRINCIPLE: Don´t repeat yourself!
    // pendiente definiciones para más adelante
    Consult saveTransactional(Consult consult, List<Exam> exams);
}