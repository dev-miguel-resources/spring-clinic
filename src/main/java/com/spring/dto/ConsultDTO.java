package com.spring.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spring.model.Medic;
import com.spring.model.Patient;
import com.spring.model.Specialty;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ConsultDTO {

    @EqualsAndHashCode.Include
    private Integer idConsult;

    @NotNull
    private Patient patient;

    @NotNull
    private Medic medic;

    @NotNull
    private Specialty specialty;

    @NotNull
    private String numConsult;

    @NotNull
    private LocalDateTime consultDate;

    @JsonManagedReference
    @NotNull
    private List<ConsultDetailDTO> details;
    
}
