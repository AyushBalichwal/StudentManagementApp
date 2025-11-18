package com.example.studentmanagementapp.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.studentmanagementapp.entity.Student;
import com.example.studentmanagementapp.exception.ResourceNotFoundException;
import com.example.studentmanagementapp.repository.StudentRepository;
import com.example.studentmanagementapp.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    @Override
    public Page<Student> getStudents(String branch, Integer yop, Pageable pageable) {
        boolean hasBranch = branch != null && !branch.isBlank();
        boolean hasYop = yop != null;

        if (hasBranch && hasYop) {
            List<Student> students = studentRepository.findByBranchIgnoreCaseAndYop(branch, yop);
            return toPage(students, pageable);
        }
        if (hasBranch) {
            List<Student> students = studentRepository.findByBranchIgnoreCase(branch);
            return toPage(students, pageable);
        }
        if (hasYop) {
            List<Student> students = studentRepository.findByYop(yop);
            return toPage(students, pageable);
        }

        return studentRepository.findAll(pageable);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student existing = getStudentById(id);

        if (student.getFullName() != null) {
            existing.setFullName(student.getFullName());
        }
        if (student.getEmail() != null) {
            existing.setEmail(student.getEmail());
        }
        if (student.getPhone() != null) {
            existing.setPhone(student.getPhone());
        }
        if (student.getBranch() != null) {
            existing.setBranch(student.getBranch());
        }
        if (student.getYop() != null) {
            existing.setYop(student.getYop());
        }
        if (student.getActive() != null) {
            existing.setActive(student.getActive());
        }

        return studentRepository.save(existing);
    }

    @Override
    public void deleteStudent(Long id) {
        Student existing = getStudentById(id);
        existing.setActive(Boolean.FALSE);
        studentRepository.save(existing);
    }

    private Page<Student> toPage(List<Student> students, Pageable pageable) {
        return new PageImpl<>(students, pageable, students.size());
    }
}
