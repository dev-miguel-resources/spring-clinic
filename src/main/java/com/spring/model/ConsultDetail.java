package com.spring.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ConsultDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idDetail;

    @Column(length = 70, nullable = false)
    private String diagnosis;

    @Column(length = 300, nullable = false)
    private String treatment;

    @ManyToOne // FK
    @JoinColumn(name = "id_consult", nullable = false, foreignKey = @ForeignKey(name = "FK_CONSULT_DETAIL"))
    private Consult consult;
    
}
