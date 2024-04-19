package com.spring.controller;

import java.net.URI;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.spring.dto.MedicDTO;
//import com.spring.dto.MedicRecord;
//import com.spring.dto.MedicRecord;
import com.spring.model.Medic;
import com.spring.service.IMedicService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/medics")
@RequiredArgsConstructor
public class MedicController {

    private final IMedicService service;

    @Qualifier("medicMapper")
    private final ModelMapper mapper;

    private MedicDTO convertToDto(Medic obj) {
        return mapper.map(obj, MedicDTO.class);
    }

    private Medic convertToEntity(MedicDTO dto) {
        return mapper.map(dto, Medic.class);
    }

    // debo traer todos los servicios implementados para crear mis apis rest
    
    // public ResponseEntity<List<MedicRecord>> findAll() {
    @PreAuthorize("@authorizeLogic.hasAccess('findAll')")
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")    
    @GetMapping
    public ResponseEntity<List<MedicDTO>> findAll() {
        // forma 1 genérica
        /*
         * List<MedicDTO> listExample = service.findAll().stream().map(e ->
         * convertToDto(e)).toList();
         * return new ResponseEntity<>(listExample, HttpStatus.OK);
         */
        // forma 2 moderna
        List<MedicDTO> list = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
        // forma 3
        /*
         * List<MedicRecord> list = service.findAll().stream().map(e ->
         * new MedicRecord(e.getIdMedic(), e.getFirstName(), e.getLastName(),
         * e.getDni(), e.getAddress(), e.getPhone(), e.getEmail())
         * ).toList();
         * return new ResponseEntity<>(list, HttpStatus.OK);
         */
    }

    @PostMapping() // nivel de madurez 3
    public ResponseEntity<MedicDTO> save(@Valid @RequestBody MedicDTO dto) {
        Medic obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdMedic())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicDTO> findById(@PathVariable("id") Integer id) {
        Medic obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    // forma 2 con hateoas
    @GetMapping("/hateoas/{id}")
    public EntityModel<MedicDTO> findByHateoas(@PathVariable("id") Integer id) {
        EntityModel<MedicDTO> resource = EntityModel.of(convertToDto(service.findById(id))); // la salida

        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));

        resource.add(link1.withRel("medic-info1"));
        resource.add(link1.withRel("medic-info2"));

        return resource;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medic> update(@PathVariable("id") Integer id, @RequestBody MedicDTO dto)
            throws Exception {
        Medic obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(obj, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Medic> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
