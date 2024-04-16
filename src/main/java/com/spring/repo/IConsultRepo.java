package com.spring.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.model.Consult;

public interface IConsultRepo extends IGenericRepo<Consult, Integer> {

    // pendiente de querys
    // 1. filtro de busqueda
    @Query("FROM Consult c WHERE c.patient.dni = :dni OR LOWER(c.patient.firstName) LIKE %:fullname% OR LOWER(c.patient.lastName) LIKE %:fullname%")
    List<Consult> search(@Param("dni") String dni, @Param("fullname") String fullname);

    // 2. filtro por rango de fechas
    @Query("FROM Consult c WHERE c.consultDate BETWEEN :date1 AND :date2")
    List<Consult> searchByDates(@Param("date1") LocalDateTime date1, @Param("date2") LocalDateTime date2);

    // 3. ejemplo desde un proc. almacenado nativo: trabajan los resultados como un
    // array de objetos
    @Query(value = "select * from fn_list()", nativeQuery = true)
    List<Object[]> callProcedureOrFunctionNative();

    // 4. ejemplo desde un proc. almacenado mediante projection
    @Query(value = "select * from fn_list()", nativeQuery = true)
    List<?> callProcedureOrFunctionProjection();
}