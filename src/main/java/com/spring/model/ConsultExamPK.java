package com.spring.model;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class ConsultExamPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_consult", nullable = false)
    private Consult consult;

    @ManyToOne
    @JoinColumn(name = "id_exam", nullable = false)
    private Exam exam;
    
}
