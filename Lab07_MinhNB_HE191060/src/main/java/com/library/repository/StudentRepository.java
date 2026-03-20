package com.library.repository;

import com.library.dao.StudentDAO;
import com.library.entity.Student;
import java.util.List;

public class StudentRepository implements IStudentRepository {
    private final StudentDAO studentDAO = new StudentDAO();

    @Override
    public void save(Student student) {
        studentDAO.save(student);
    }

    @Override
    public Student findById(Long id) {
        return studentDAO.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return studentDAO.findAll();
    }

    @Override
    public void update(Student student) {
        studentDAO.update(student);
    }

    @Override
    public void delete(Long id) {
        studentDAO.delete(id);
    }
}
