package fu.se.lab3_he191060.repositories;

import fu.se.lab3_he191060.pojos.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("save() persists and findById() retrieves employee")
    void save_and_findById() {
        Employee emp = new Employee("E01", "John", "Dev", 1000.0);

        employeeRepository.save(emp);

        Optional<Employee> found = employeeRepository.findById("E01");
        assertTrue(found.isPresent());
        assertEquals("John", found.get().getName());
        assertEquals("Dev", found.get().getDesignation());
        assertEquals(1000.0, found.get().getSalary());
    }

    @Test
    @DisplayName("findAll() returns all persisted employees")
    void findAll_returnsAll() {
        Employee e1 = new Employee("E01", "John", "Dev", 1000.0);
        Employee e2 = new Employee("E02", "Jane", "Tester", 900.0);

        employeeRepository.save(e1);
        employeeRepository.save(e2);

        List<Employee> all = employeeRepository.findAll();

        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("save() updates existing employee when same id")
    void save_updatesExisting() {
        Employee emp = new Employee("E01", "John", "Dev", 1000.0);
        employeeRepository.save(emp);

        Employee updated = new Employee("E01", "Johnny", "Lead Dev", 2000.0);
        employeeRepository.save(updated);

        Employee fromDb = employeeRepository.findById("E01").orElseThrow();
        assertEquals("Johnny", fromDb.getName());
        assertEquals("Lead Dev", fromDb.getDesignation());
        assertEquals(2000.0, fromDb.getSalary());
    }

    @Test
    @DisplayName("deleteById() removes employee")
    void deleteById_removesEmployee() {
        Employee emp = new Employee("E01", "John", "Dev", 1000.0);
        employeeRepository.save(emp);
        assertTrue(employeeRepository.findById("E01").isPresent());

        employeeRepository.deleteById("E01");

        assertFalse(employeeRepository.findById("E01").isPresent());
    }

    @Test
    @DisplayName("Entity has generated persistence metadata")
    void entity_isManagedByJPA() {
        Employee emp = new Employee("E01", "John", "Dev", 1000.0);
        Employee saved = employeeRepository.save(emp);

        assertNotNull(saved);
        assertEquals("E01", saved.getEmpId());
    }
}

