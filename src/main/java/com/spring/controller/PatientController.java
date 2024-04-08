package com.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.service.IPatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final IPatientService service;

    // debo traer todos los servicios implementados para crear mis apis rest
    /*@GetMapping
    public ResponseEntity<List<?>> findAll() {}*/ 
    /*@PostMapping()
    public ResponseEntity<?> save() {}*/ 
    /*@GetMapping("/{id}")
    public ResponseEntity<?> findById() {}*/
    /*@PutMapping("/{id}")
    public ResponseEntity<?> update() {}*/
    /*@DeleteMapping("/{id}")
    public ResponseEntity<?> delete() {}*/

}
