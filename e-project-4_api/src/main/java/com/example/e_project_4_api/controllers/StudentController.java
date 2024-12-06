package com.example.e_project_4_api.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@RestController
//
//public class StudentController {
//
//    private List<Student> students = new ArrayList<>(
//            Arrays.asList(
//                    new Student(1, "Navin", 60),
//                    new Student(2, "Kiran", 65)
//            ));
//
//
//    @GetMapping("/user/students")
//    @PreAuthorize("hasRole('USER')")
//    public List<Student> getStudents() {
//        return students;
//    }
//
//    @GetMapping("/csrf-token")
//    public CsrfToken getCsrfToken(HttpServletRequest request) {
//        return (CsrfToken) request.getAttribute("_csrf");
//
//
//    }
//
//
//    @PostMapping("/artist/students")
//    @PreAuthorize("hasRole('ARTIST')")
//    public Student addStudent(@RequestBody Student student) {
//        students.add(student);
//        return student;
//    }
//
//}
