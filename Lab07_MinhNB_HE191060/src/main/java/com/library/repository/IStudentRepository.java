package com.library.repository;

import com.library.entity.Student;
import java.util.List;

public interface IStudentRepository {
    void save(Student student);
    Student findById(Long id);
    List<Student> findAll();
    void update(Student student);
    void delete(Long id);
}
