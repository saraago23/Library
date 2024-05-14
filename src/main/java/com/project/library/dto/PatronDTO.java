package com.project.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatronDTO {

    private Integer id;
    private UserDTO user;
    @JsonIgnore
    private Boolean deleted;
}
