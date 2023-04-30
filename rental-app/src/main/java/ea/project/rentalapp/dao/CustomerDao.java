package ea.project.rentalapp.dao;

import ea.project.rentalapp.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerDao extends JpaRepository<Customer, Long> {
    List<Customer> findByName(String name);
    Optional<Customer> findByCustomerNumber(String customerNumber);

    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);

}
