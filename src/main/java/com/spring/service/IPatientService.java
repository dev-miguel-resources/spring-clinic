package com.spring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.spring.model.Patient;

public interface IPatientService extends ICRUD<Patient, Integer> {

    // DRY PRINCIPLE: Don´t repeat yourself!
    Page<Patient> listPage(Pageable pageable);
    
}
