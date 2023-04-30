package ea.project.rentalapp.dao;

import ea.project.rentalapp.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDao extends JpaRepository<Employee, Long> {


}
