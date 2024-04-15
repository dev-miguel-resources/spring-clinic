package com.spring.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.dto.MedicDTO;
import com.spring.model.Medic;

@Configuration
public class MapperConfig {

    // te devuelve un conversor de datos
    @Bean("defaultMapper")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean("medicMapper")
    public ModelMapper medicMapper() {
        ModelMapper mapper = new ModelMapper();

        // Escritura
        TypeMap<MedicDTO, Medic> typeMap1 = mapper.createTypeMap(MedicDTO.class, Medic.class);
        typeMap1.addMapping(MedicDTO::getIdMedic, (dest, v) -> dest.setIdMedic((Integer) v));
        typeMap1.addMapping(MedicDTO::getPrimaryName, (dest, v) -> dest.setFirstName((String) v));
        typeMap1.addMapping(MedicDTO::getSurname, (dest, v) -> dest.setLastName((String) v));
        typeMap1.addMapping(MedicDTO::getCodMedic, (dest, v) -> dest.setCodMed((String) v));
        typeMap1.addMapping(MedicDTO::getPhoto, (dest, v) -> dest.setPhotoUrl((String) v));

        // Lectura
        TypeMap<Medic, MedicDTO> typeMap2 = mapper.createTypeMap(Medic.class, MedicDTO.class);
        typeMap2.addMapping(Medic::getIdMedic, (dest, v) -> dest.setIdMedic((Integer) v));
        typeMap2.addMapping(Medic::getFirstName, (dest, v) -> dest.setPrimaryName((String) v));
        typeMap2.addMapping(Medic::getLastName, (dest, v) -> dest.setSurname((String) v));
        typeMap2.addMapping(Medic::getCodMed, (dest, v) -> dest.setCodMedic((String) v));
        typeMap2.addMapping(Medic::getPhotoUrl, (dest, v) -> dest.setPhoto((String) v));

        return mapper;

    }

}
