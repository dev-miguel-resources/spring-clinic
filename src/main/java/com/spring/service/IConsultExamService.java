package com.spring.service;

import java.util.List;

import com.spring.model.ConsultExam;

public interface IConsultExamService {

    List<ConsultExam> getExamsByConsultId(Integer idConsult);

}
