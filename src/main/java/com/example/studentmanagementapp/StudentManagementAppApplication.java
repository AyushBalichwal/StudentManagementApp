package com.example.studentmanagementapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StudentManagementAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementAppApplication.class, args);
    }

}
