package com.example.studentmanagementapp.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentmanagementapp.dto.StudentRequestDTO;
import com.example.studentmanagementapp.dto.StudentResponseDTO;
import com.example.studentmanagementapp.entity.Student;
import com.example.studentmanagementapp.mapper.StudentMapper;
import com.example.studentmanagementapp.service.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Validated
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentRequestDTO requestDTO) {
        Student created = studentService.createStudent(StudentMapper.toEntity(requestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(StudentMapper.toDTO(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(StudentMapper.toDTO(student));
    }

    @GetMapping
    public ResponseEntity<Page<StudentResponseDTO>> getStudents(
            @RequestParam(value = "branch", required = false) String branch,
            @RequestParam(value = "yop", required = false) Integer yop,
            Pageable pageable) {
        Page<StudentResponseDTO> response = studentService.getStudents(branch, yop, pageable)
                .map(StudentMapper::toDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id,
            @Valid @RequestBody StudentRequestDTO requestDTO) {
        Student updated = studentService.updateStudent(id, StudentMapper.toEntity(requestDTO));
        return ResponseEntity.ok(StudentMapper.toDTO(updated));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> partialUpdateStudent(@PathVariable Long id,
            @RequestBody StudentRequestDTO requestDTO) {
        Student updated = studentService.updateStudent(id, StudentMapper.toEntity(requestDTO));
        return ResponseEntity.ok(StudentMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(Map.of("message", "Student deleted successfully"));
    }
}
