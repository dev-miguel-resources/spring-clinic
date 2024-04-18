package com.spring.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(ConsultExamPK.class)
// pendiente
public class ConsultExam {

    @Id
    private Consult consult;

    @Id
    private Exam exam;

    public ConsultExam(Exam exam) {
        this.exam = exam;
    }
}
