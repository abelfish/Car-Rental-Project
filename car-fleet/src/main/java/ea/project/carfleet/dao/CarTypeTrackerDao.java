package ea.project.carfleet.dao;

import ea.project.carfleet.domain.CarType;
import ea.project.carfleet.domain.CarTypeTracker;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CarTypeTrackerDao extends MongoRepository<CarTypeTracker, String> {

        Optional<CarTypeTracker> findByCarType(CarType carType);
}
