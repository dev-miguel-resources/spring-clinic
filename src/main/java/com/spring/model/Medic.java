package com.spring.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Medic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idMedic;

    @Column(length = 70, nullable = false)
    private String firstName;
    
    @Column(length = 70, nullable = false)
    private String lastName;

    @Column(length = 12, nullable = false)
    private String codMed;

    private String photoUrl;
}
