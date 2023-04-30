package ea.project.rentalapp.dao;

import ea.project.rentalapp.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationDao extends JpaRepository<Reservation, Long> {
}
