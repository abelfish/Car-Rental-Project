package ea.project.carfleet.service;

import ea.project.carfleet.domain.CarType;
import ea.project.carfleet.domain.CarTypeTracker;
import ea.project.carfleet.service.dto.CarTypeTrackerDTO;

import java.util.List;

public interface CarTypeTrackerService {

        CarTypeTracker incrementCarTypeTracker(CarType carType);
        CarTypeTracker decrementCarTypeTracker(CarType carType);
        List<CarTypeTrackerDTO> getAllCarTypes();
        CarTypeTrackerDTO getCarType(CarType carType);
}
