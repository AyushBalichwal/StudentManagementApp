package com.example.studentmanagementapp.mapper;

import com.example.studentmanagementapp.dto.StudentRequestDTO;
import com.example.studentmanagementapp.dto.StudentResponseDTO;
import com.example.studentmanagementapp.entity.Student;

public final class StudentMapper {

    private StudentMapper() {
    }

    public static Student toEntity(StudentRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Student.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .branch(dto.getBranch())
                .yop(dto.getYop())
                .active(dto.getActive())
                .build();
    }

    public static StudentResponseDTO toDTO(Student student) {
        if (student == null) {
            return null;
        }
        return StudentResponseDTO.builder()
                .id(student.getId())
                .fullName(student.getFullName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .branch(student.getBranch())
                .yop(student.getYop())
                .active(student.getActive())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}
