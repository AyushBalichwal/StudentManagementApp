package com.example.studentmanagementapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.studentmanagementapp.entity.Student;

public interface StudentService {

    Student createStudent(Student student);

    Student getStudentById(Long id);

    Page<Student> getStudents(String branch, Integer yop, Pageable pageable);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);
}
