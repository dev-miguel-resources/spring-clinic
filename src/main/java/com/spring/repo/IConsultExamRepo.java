package com.spring.repo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.spring.model.ConsultExam;

public interface IConsultExamRepo extends IGenericRepo<ConsultExam, Integer> {

    // pendiente de querys
    // 1. guardar las referencias de los id exámenes de acuerdo a un id de consulta (save)
    // realizaremos esta query de manera nativa: apuntan a la referencia del nombre de la tabla
    @Modifying
    @Query(value = "INSERT INTO consult_exam(id_consult, id_exam) VALUES (:idConsult, :idExam)", nativeQuery = true)
    Integer saveExam(@Param("idConsult") Integer idConsult, @Param("idExam") Integer idExam);

    // 2. obtener los exámenes por el id de la consulta
    
}
 