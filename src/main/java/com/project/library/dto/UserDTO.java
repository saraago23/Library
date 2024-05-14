package com.project.library.dto;

import com.project.library.annotations.AdultAge;
import com.project.library.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;
    @NotNull(message = "Please insert username ")
    private String username;
    @NotNull(message = "Please insert password ")
    private String password;
    private Role role;
    @Email(message = "Email format not correct")
    private String email;
    private String firstName;
    private String lastName;
    @AdultAge(message = "You must be at least 18 years of age to register")
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
    @JsonIgnore
    private boolean deleted;
}
