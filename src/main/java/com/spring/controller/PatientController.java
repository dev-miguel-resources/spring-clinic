package com.spring.controller;

import java.net.URI;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.dto.PatientDTO;
//import com.spring.dto.PatientRecord;
import com.spring.model.Patient;
import com.spring.service.IPatientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final IPatientService service;

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    private PatientDTO convertToDto(Patient obj) {
        return mapper.map(obj, PatientDTO.class);
    }

    private Patient convertToEntity(PatientDTO dto) {
        return mapper.map(dto, Patient.class);
    }

    // debo traer todos los servicios implementados para crear mis apis rest
    @GetMapping
    //public ResponseEntity<List<PatientRecord>> findAll() {
    public ResponseEntity<List<PatientDTO>> findAll() {
        // forma 1 genérica
        /*List<PatientDTO> listExample = service.findAll().stream().map(e -> convertToDto(e)).toList();
        return new ResponseEntity<>(listExample, HttpStatus.OK);*/
        // forma 2 moderna
        List<PatientDTO> list = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
        // forma 3
        /*List<PatientRecord> list = service.findAll().stream().map(e -> 
            new PatientRecord(e.getIdPatient(), e.getFirstName(), e.getLastName(), 
            e.getDni(), e.getAddress(), e.getPhone(), e.getEmail())
        ).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);*/
    } 
    @PostMapping() // nivel de madurez 3
    public ResponseEntity<PatientDTO> save(@Valid @RequestBody PatientDTO dto) {
        Patient obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPatient()).toUri();
        return ResponseEntity.created(location).build();
    } 
    /*@GetMapping("/{id}")
    public ResponseEntity<?> findById() {}*/
    /*@PutMapping("/{id}")
    public ResponseEntity<?> update() {}*/
    /*@DeleteMapping("/{id}")
    public ResponseEntity<?> delete() {}*/

}
