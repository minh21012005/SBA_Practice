package fu.se.lab3_he191060.services;

import fu.se.lab3_he191060.pojos.Employee;
import fu.se.lab3_he191060.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findById(String empId) {
        return employeeRepository.findById(empId);
    }

    @Override
    public Employee save(Employee employee) {
        validateForCreate(employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(String empId, Employee employee) {
        validateForUpdate(employee);
        return employeeRepository.findById(empId)
                .map(existing -> {
                    existing.setName(employee.getName());
                    existing.setDesignation(employee.getDesignation());
                    existing.setSalary(employee.getSalary());
                    return employeeRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + empId));
    }

    private void validateForCreate(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Dữ liệu nhân viên không được null");
        }
        if (employee.getEmpId() == null || employee.getEmpId().isBlank()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống");
        }
        if (employee.getEmpId().length() > 20) {
            throw new IllegalArgumentException("Mã nhân viên tối đa 20 ký tự");
        }
        validateNameDesignationSalary(employee);
    }

    private void validateForUpdate(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Dữ liệu nhân viên không được null");
        }
        validateNameDesignationSalary(employee);
    }

    private void validateNameDesignationSalary(Employee employee) {
        if (employee.getName() == null || employee.getName().isBlank()) {
            throw new IllegalArgumentException("Tên không được để trống");
        }
        if (employee.getName().length() > 100) {
            throw new IllegalArgumentException("Tên tối đa 100 ký tự");
        }
        if (employee.getDesignation() == null || employee.getDesignation().isBlank()) {
            throw new IllegalArgumentException("Chức vụ không được để trống");
        }
        if (employee.getDesignation().length() > 100) {
            throw new IllegalArgumentException("Chức vụ tối đa 100 ký tự");
        }
        if (employee.getSalary() < 0) {
            throw new IllegalArgumentException("Lương phải lớn hơn hoặc bằng 0");
        }
    }

    @Override
    public void deleteById(String empId) {
        employeeRepository.deleteById(empId);
    }
}
