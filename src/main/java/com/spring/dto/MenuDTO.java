package com.spring.dto;

import java.util.List;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MenuDTO {

    @EqualsAndHashCode.Include
    private Integer idMenu;

    private String icon;
    private String name;
    private String url;
    private List<RolDTO> roles;
    
}
