package com.spring.service;

import java.time.LocalDateTime;
import java.util.List;

import com.spring.dto.ConsultProcDTO;
import com.spring.dto.IConsultProcDTO;
import com.spring.model.Consult;
import com.spring.model.Exam;

public interface IConsultService extends ICRUD<Consult, Integer> {

    // DRY PRINCIPLE: Don´t repeat yourself!
    Consult saveTransactional(Consult consult, List<Exam> exams);
    List<Consult> search(String dni, String fullname);
    List<Consult> searchByDates(LocalDateTime date1, LocalDateTime date2);
    List<ConsultProcDTO> callProcedureOrFunctionNative();
    List<IConsultProcDTO> callProcedureOrFunctionProjection();
    byte[] generateReport() throws Exception;

}