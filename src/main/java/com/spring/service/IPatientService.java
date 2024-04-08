package com.spring.service;

import com.spring.model.Patient;

public interface IPatientService extends ICRUD<Patient, Integer> {

    // DRY PRINCIPLE: Don´t repeat yourself!
    // definiciones para más adelante
}
