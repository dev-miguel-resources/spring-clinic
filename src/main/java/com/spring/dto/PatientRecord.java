package com.spring.dto;

// Se encuentran disponibles desde java 15 en adelante
// sirven para crear objetos inmutables: no puedes trabajar con getters ni setters, por tanto,
// no podría modificar sus atributos
public record PatientRecord(

        Integer idPatient,
        String primaryName,
        String surname,
        String dni,
        String address,
        String phone,
        String email) {
}
