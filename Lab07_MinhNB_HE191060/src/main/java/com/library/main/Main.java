package com.library.main;

import com.library.entity.Book;
import com.library.entity.Student;
import com.library.service.IStudentService;
import com.library.service.StudentService;
import com.library.util.HibernateUtil;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        IStudentService studentService = new StudentService();

        try {
            System.out.println("--- 1. Create Students ---");
            Student s1 = Student.builder()
                    .firstName("Minh")
                    .lastName("Nguyen")
                    .email("minh@example.com")
                    .password("123456")
                    .marks(9.5)
                    .build();

            Student s2 = Student.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("john@example.com")
                    .password("qwerty")
                    .marks(8.0)
                    .build();

            studentService.createStudent(s1);
            studentService.createStudent(s2);
            System.out.println("Students created successfully.");

            System.out.println("\n--- 2. Retrieve All Students ---");
            List<Student> students = studentService.getAllStudents();
            students.forEach(System.out::println);

            System.out.println("\n--- 3. Update Student ---");
            if (!students.isEmpty()) {
                Student toUpdate = students.get(0);
                toUpdate.setMarks(10.0);
                toUpdate.setFirstName("Minh Updated");
                studentService.updateStudent(toUpdate);
                System.out.println("Updated Student: " + studentService.getStudentById(toUpdate.getId()));
            }

            System.out.println("\n--- 4. Assign Books to Student ---");
            Book b1 = Book.builder().title("Hibernate in Action").author("Christian Bauer").isbn("978-1932394153").build();
            Book b2 = Book.builder().title("Clean Code").author("Robert C. Martin").isbn("978-0132350884").build();

            Student activeStudent = studentService.getStudentById(1L);
            if (activeStudent != null) {
                activeStudent.addBook(b1);
                activeStudent.addBook(b2);
                studentService.updateStudent(activeStudent);
                System.out.println("Books assigned to: " + activeStudent.getFirstName());
                
                // Reload to see books
                Student reloaded = studentService.getStudentById(1L);
                System.out.println("Student with books: " + reloaded);
                System.out.println("Books count: " + reloaded.getBooks().size());
                reloaded.getBooks().forEach(System.out::println);
            }

            System.out.println("\n--- 5. Delete Student ---");
            if (students.size() > 1) {
                Long idToDelete = students.get(1).getId();
                studentService.deleteStudent(idToDelete);
                System.out.println("Deleted Student ID: " + idToDelete);
            }

            System.out.println("\n--- Final List of Students ---");
            studentService.getAllStudents().forEach(System.out::println);

        } finally {
            HibernateUtil.shutdown();
        }
    }
}
