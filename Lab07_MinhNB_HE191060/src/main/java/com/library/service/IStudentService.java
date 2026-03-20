package com.library.service;

import com.library.entity.Student;
import java.util.List;

public interface IStudentService {
    void createStudent(Student student);
    Student getStudentById(Long id);
    List<Student> getAllStudents();
    void updateStudent(Student student);
    void deleteStudent(Long id);
}
