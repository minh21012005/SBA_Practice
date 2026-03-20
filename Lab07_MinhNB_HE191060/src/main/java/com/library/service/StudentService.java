package com.library.service;

import com.library.entity.Student;
import com.library.repository.IStudentRepository;
import com.library.repository.StudentRepository;
import java.util.List;

public class StudentService implements IStudentService {
    private final IStudentRepository studentRepository = new StudentRepository();

    @Override
    public void createStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void updateStudent(Student student) {
        studentRepository.update(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.delete(id);
    }
}
