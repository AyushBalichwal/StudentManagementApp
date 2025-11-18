package com.example.studentmanagementapp.dto;

import java.time.LocalDateTime;

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
public class StudentResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String branch;
    private Integer yop;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
