package com.spring.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RolDTO {

    @EqualsAndHashCode.Include
    private Integer idRol;
    private String name;
    private String description;
    
}
