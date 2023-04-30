package ea.project.rentalapp.dao;

import ea.project.rentalapp.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDao extends JpaRepository<Address, Long> {
}
