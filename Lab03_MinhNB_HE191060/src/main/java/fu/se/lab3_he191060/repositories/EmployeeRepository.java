package fu.se.lab3_he191060.repositories;

import fu.se.lab3_he191060.pojos.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
