package com.spring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultExamDTO {

    private ConsultDTO consult;

    private ExamDTO exam;
    
}
