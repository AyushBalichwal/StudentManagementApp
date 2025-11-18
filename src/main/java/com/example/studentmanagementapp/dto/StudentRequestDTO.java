package com.example.studentmanagementapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequestDTO {

    @NotBlank
    private String fullName;

    @Email
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String branch;

    @NotNull
    private Integer yop;

    @NotNull
    private Boolean active;
}
