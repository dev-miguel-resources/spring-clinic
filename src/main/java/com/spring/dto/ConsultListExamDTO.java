package com.spring.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultListExamDTO {

    @NotNull
    private ConsultDTO consult;

    private List<ExamDTO> lstExam;
    
}
