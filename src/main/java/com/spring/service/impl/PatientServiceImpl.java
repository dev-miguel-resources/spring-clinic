package com.spring.service.impl;

import org.springframework.stereotype.Service;

import com.spring.model.Patient;
import com.spring.repo.IGenericRepo;
import com.spring.repo.IPatientRepo;
import com.spring.service.IPatientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl extends CRUDImpl<Patient, Integer> implements IPatientService {

    private final IPatientRepo repo;

    @Override
    protected IGenericRepo<Patient, Integer> getRepo() {
        return repo;
    }

    // definiciones para más adelante
    
}
