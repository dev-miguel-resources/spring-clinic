package com.spring.controller;

import java.net.URI;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.dto.SpecialtyDTO;
//import com.spring.dto.SpecialtyRecord;
//import com.spring.dto.SpecialtyRecord;
import com.spring.model.Specialty;
import com.spring.service.ISpecialtyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/specialtys")
@RequiredArgsConstructor
public class SpecialtyController {

    private final ISpecialtyService service;

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    private SpecialtyDTO convertToDto(Specialty obj) {
        return mapper.map(obj, SpecialtyDTO.class);
    }

    private Specialty convertToEntity(SpecialtyDTO dto) {
        return mapper.map(dto, Specialty.class);
    }

    // debo traer todos los servicios implementados para crear mis apis rest
    @GetMapping
    // public ResponseEntity<List<SpecialtyRecord>> findAll() {
    public ResponseEntity<List<SpecialtyDTO>> findAll() {
        // forma 1 genérica
        /*
         * List<SpecialtyDTO> listExample = service.findAll().stream().map(e ->
         * convertToDto(e)).toList();
         * return new ResponseEntity<>(listExample, HttpStatus.OK);
         */
        // forma 2 moderna
        List<SpecialtyDTO> list = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
        // forma 3
        /*
         * List<SpecialtyRecord> list = service.findAll().stream().map(e ->
         * new SpecialtyRecord(e.getIdSpecialty(), e.getFirstName(), e.getLastName(),
         * e.getDni(), e.getAddress(), e.getPhone(), e.getEmail())
         * ).toList();
         * return new ResponseEntity<>(list, HttpStatus.OK);
         */
    }

    @PostMapping() // nivel de madurez 3
    public ResponseEntity<SpecialtyDTO> save(@Valid @RequestBody SpecialtyDTO dto) {
        Specialty obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdSpecialty())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> findById(@PathVariable("id") Integer id) {
        Specialty obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    // forma 2 con hateoas
    @GetMapping("/hateoas/{id}")
    public EntityModel<SpecialtyDTO> findByHateoas(@PathVariable("id") Integer id) {
        EntityModel<SpecialtyDTO> resource = EntityModel.of(convertToDto(service.findById(id))); // la salida

        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));

        resource.add(link1.withRel("Specialty-info1"));
        resource.add(link1.withRel("Specialty-info2"));

        return resource;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Specialty> update(@PathVariable("id") Integer id, @RequestBody SpecialtyDTO dto)
            throws Exception {
        Specialty obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(obj, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Specialty> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
