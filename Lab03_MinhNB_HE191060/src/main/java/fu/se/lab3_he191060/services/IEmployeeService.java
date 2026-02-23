package fu.se.lab3_he191060.services;

import fu.se.lab3_he191060.pojos.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    List<Employee> findAll();

    Optional<Employee> findById(String empId);

    Employee save(Employee employee);

    Employee update(String empId, Employee employee);

    void deleteById(String empId);
}
