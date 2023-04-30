package ea.project.carfleet.dao;

import ea.project.carfleet.domain.RentalStatus;
import ea.project.carfleet.domain.RentalStatusTracker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface RentalStatusTrackerDao extends MongoRepository<RentalStatusTracker, String> {

    Optional<RentalStatusTracker> findByRentalStatus(RentalStatus rentalStatus);
}
