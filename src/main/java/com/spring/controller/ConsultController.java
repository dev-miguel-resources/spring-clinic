package com.spring.controller;

import java.net.URI;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.dto.ConsultDTO;
import com.spring.dto.ConsultListExamDTO;
//import com.spring.dto.ConsultRecord;
import com.spring.model.Consult;
import com.spring.model.Exam;
import com.spring.service.IConsultService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/consults")
@RequiredArgsConstructor
public class ConsultController {

    private final IConsultService service;

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    private ConsultDTO convertToDto(Consult obj) {
        return mapper.map(obj, ConsultDTO.class);
    }

    private Consult convertToEntity(ConsultDTO dto) {
        return mapper.map(dto, Consult.class);
    }

    // debo traer todos los servicios implementados para crear mis apis rest
    @GetMapping
    // public ResponseEntity<List<ConsultRecord>> findAll() {
    public ResponseEntity<List<ConsultDTO>> findAll() {
        // forma 1 genérica
        /*
         * List<ConsultDTO> listExample = service.findAll().stream().map(e ->
         * convertToDto(e)).toList();
         * return new ResponseEntity<>(listExample, HttpStatus.OK);
         */
        // forma 2 moderna
        List<ConsultDTO> list = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
        // forma 3
        /*
         * List<ConsultRecord> list = service.findAll().stream().map(e ->
         * new ConsultRecord(e.getIdConsult(), e.getFirstName(), e.getLastName(),
         * e.getDni(), e.getAddress(), e.getPhone(), e.getEmail())
         * ).toList();
         * return new ResponseEntity<>(list, HttpStatus.OK);
         */
    }

    @PostMapping() // nivel de madurez 3
    public ResponseEntity<ConsultDTO> save(@Valid @RequestBody ConsultListExamDTO dto) {
        Consult cons = this.convertToEntity(dto.getConsult());

        // alternativa 1
        // List<Exam> exams = dto.getLstExam().stream().map(e -> mapper.map(e,
        // Exam.class)).toList();

        // alternativa 2
        List<Exam> exams = mapper.map(dto.getLstExam(), new TypeToken<List<Exam>>() {
        }.getType());

        Consult obj = service.saveTransactional(cons, exams);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsult())
                .toUri();
        
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultDTO> findById(@PathVariable("id") Integer id) {
        Consult obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    // forma 2 con hateoas
    @GetMapping("/hateoas/{id}")
    public EntityModel<ConsultDTO> findByHateoas(@PathVariable("id") Integer id) {
        EntityModel<ConsultDTO> resource = EntityModel.of(convertToDto(service.findById(id))); // la salida

        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));

        resource.add(link1.withRel("consult-info1"));
        resource.add(link1.withRel("consult-info2"));

        return resource;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consult> update(@PathVariable("id") Integer id, @RequestBody ConsultDTO dto)
            throws Exception {
        Consult obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(obj, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Consult> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // pendientes otros servicios a exponer para más adelante

}
