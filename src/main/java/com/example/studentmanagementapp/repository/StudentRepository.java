package com.example.studentmanagementapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.studentmanagementapp.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByBranchIgnoreCase(String branch);

    List<Student> findByYop(Integer yop);

    List<Student> findByBranchIgnoreCaseAndYop(String branch, Integer yop);
}
