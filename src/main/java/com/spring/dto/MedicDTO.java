package com.spring.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MedicDTO {

    @EqualsAndHashCode.Include
    private Integer idMedic;

    @NotNull
    @NotEmpty
    @Size(min = 3)
    private String primaryName;

    @NotNull
    @NotEmpty
    @Size(min = 3)
    private String surname;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 12)
    private String codMedic;

    @NotNull
    @NotEmpty
    private String photo;

}
