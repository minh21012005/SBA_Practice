package fu.se.lab3_he191060.services;

import fu.se.lab3_he191060.pojos.Employee;
import fu.se.lab3_he191060.repositories.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    @DisplayName("findAll() delegates to repository")
    void findAll_delegatesToRepository() {
        List<Employee> expected = List.of(new Employee("E01", "A", "Dev", 1));
        when(employeeRepository.findAll()).thenReturn(expected);

        List<Employee> actual = employeeService.findAll();

        assertSame(expected, actual);
        verify(employeeRepository).findAll();
    }

    @Test
    @DisplayName("findById() delegates to repository")
    void findById_delegatesToRepository() {
        Employee expected = new Employee("E01", "A", "Dev", 1);
        when(employeeRepository.findById("E01")).thenReturn(Optional.of(expected));

        Optional<Employee> actual = employeeService.findById("E01");

        assertEquals(Optional.of(expected), actual);
        verify(employeeRepository).findById("E01");
    }

    @Test
    @DisplayName("save() throws when employee is null")
    void save_nullEmployee_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> employeeService.save(null));
        assertEquals("Dữ liệu nhân viên không được null", ex.getMessage());
    }

    @Test
    @DisplayName("save() throws when empId is blank")
    void save_blankEmpId_throws() {
        Employee e = new Employee("  ", "A", "Dev", 1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> employeeService.save(e));
        assertEquals("Mã nhân viên không được để trống", ex.getMessage());
    }

    @Test
    @DisplayName("save() throws when empId length > 20")
    void save_empIdTooLong_throws() {
        Employee e = new Employee("123456789012345678901", "A", "Dev", 1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> employeeService.save(e));
        assertEquals("Mã nhân viên tối đa 20 ký tự", ex.getMessage());
    }

    @Test
    @DisplayName("save() throws when name is blank")
    void save_blankName_throws() {
        Employee e = new Employee("E01", " ", "Dev", 1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> employeeService.save(e));
        assertEquals("Tên không được để trống", ex.getMessage());
    }

    @Test
    @DisplayName("save() throws when name length > 100")
    void save_nameTooLong_throws() {
        String longName = "a".repeat(101);
        Employee e = new Employee("E01", longName, "Dev", 1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> employeeService.save(e));
        assertEquals("Tên tối đa 100 ký tự", ex.getMessage());
    }

    @Test
    @DisplayName("save() throws when designation is blank")
    void save_blankDesignation_throws() {
        Employee e = new Employee("E01", "A", " ", 1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> employeeService.save(e));
        assertEquals("Chức vụ không được để trống", ex.getMessage());
    }

    @Test
    @DisplayName("save() throws when designation length > 100")
    void save_designationTooLong_throws() {
        String longDes = "a".repeat(101);
        Employee e = new Employee("E01", "A", longDes, 1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> employeeService.save(e));
        assertEquals("Chức vụ tối đa 100 ký tự", ex.getMessage());
    }

    @Test
    @DisplayName("save() throws when salary < 0")
    void save_negativeSalary_throws() {
        Employee e = new Employee("E01", "A", "Dev", -1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> employeeService.save(e));
        assertEquals("Lương phải lớn hơn hoặc bằng 0", ex.getMessage());
    }

    @Test
    @DisplayName("save() persists employee when valid")
    void save_valid_persists() {
        Employee e = new Employee("E01", "A", "Dev", 1);
        when(employeeRepository.save(any(Employee.class))).thenReturn(e);

        Employee saved = employeeService.save(e);

        assertSame(e, saved);
        verify(employeeRepository).save(e);
    }

    @Test
    @DisplayName("update() throws when employee is null")
    void update_nullEmployee_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> employeeService.update("E01", null));
        assertEquals("Dữ liệu nhân viên không được null", ex.getMessage());
    }

    @Test
    @DisplayName("update() throws when name/designation/salary invalid")
    void update_invalidPayload_throws() {
        Employee invalid = new Employee("IGNORED", " ", "Dev", 1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> employeeService.update("E01", invalid));
        assertEquals("Tên không được để trống", ex.getMessage());
    }

    @Test
    @DisplayName("update() throws RuntimeException when employee not found")
    void update_notFound_throwsRuntimeException() {
        Employee payload = new Employee("IGNORED", "A", "Dev", 1);
        when(employeeRepository.findById("E404")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> employeeService.update("E404", payload));
        assertEquals("Employee not found with id: E404", ex.getMessage());
        verify(employeeRepository).findById("E404");
    }

    @Test
    @DisplayName("update() updates fields and saves existing employee")
    void update_found_updatesAndSaves() {
        Employee existing = new Employee("E01", "Old", "OldDes", 10);
        Employee payload = new Employee("IGNORED", "New", "NewDes", 99);

        when(employeeRepository.findById("E01")).thenReturn(Optional.of(existing));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(inv -> inv.getArgument(0));

        Employee updated = employeeService.update("E01", payload);

        assertNotNull(updated);
        assertEquals("E01", updated.getEmpId());
        assertEquals("New", updated.getName());
        assertEquals("NewDes", updated.getDesignation());
        assertEquals(99, updated.getSalary());

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());
        assertSame(existing, captor.getValue());
        assertEquals("New", captor.getValue().getName());
    }

    @Test
    @DisplayName("deleteById() delegates to repository")
    void deleteById_delegatesToRepository() {
        employeeService.deleteById("E01");
        verify(employeeRepository).deleteById("E01");
    }
}

