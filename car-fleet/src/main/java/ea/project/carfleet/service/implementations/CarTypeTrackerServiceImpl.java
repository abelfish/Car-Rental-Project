package ea.project.carfleet.service.implementations;

import ea.project.carfleet.dao.CarTypeTrackerDao;
import ea.project.carfleet.domain.CarType;
import ea.project.carfleet.domain.CarTypeTracker;
import ea.project.carfleet.service.CarTypeTrackerService;
import ea.project.carfleet.service.adapters.CarTypeAdapter;
import ea.project.carfleet.service.dto.CarTypeTrackerDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarTypeTrackerServiceImpl implements CarTypeTrackerService {

    private final CarTypeTrackerDao carTypeTrackerDao;

    public CarTypeTrackerServiceImpl(CarTypeTrackerDao carTypeTrackerDao) {
        this.carTypeTrackerDao = carTypeTrackerDao;
    }

    @Override
    public CarTypeTracker incrementCarTypeTracker(CarType type) {
        Optional<CarTypeTracker> carTypeTracker = carTypeTrackerDao.findByCarType(type);
        if (carTypeTracker.isPresent()) {
            carTypeTracker.get().setCount(carTypeTracker.get().getCount() + 1);
            return carTypeTrackerDao.save(carTypeTracker.get());
        }
        CarTypeTracker newCarTypeTracker = new CarTypeTracker();
        newCarTypeTracker.setCarType(type);
        newCarTypeTracker.setCount(1);
        return carTypeTrackerDao.save(newCarTypeTracker);
    }

    @Override
    public CarTypeTracker decrementCarTypeTracker(CarType type) {
        Optional<CarTypeTracker> carTypeTracker = carTypeTrackerDao.findByCarType(type);
        if (carTypeTracker.isPresent()) {
            carTypeTracker.get().setCount(carTypeTracker.get().getCount() - 1);
            return carTypeTrackerDao.save(carTypeTracker.get());
        }
        CarTypeTracker newCarTypeTracker = new CarTypeTracker();
        newCarTypeTracker.setCarType(type);
        newCarTypeTracker.setCount(1);
        return carTypeTrackerDao.save(newCarTypeTracker);
    }

    @Override
    public List<CarTypeTrackerDTO> getAllCarTypes() {
        return CarTypeAdapter.toCarTypeTrackerDTOList(carTypeTrackerDao.findAll());
    }

    @Override
    public CarTypeTrackerDTO getCarType(CarType carType) {
        Optional<CarTypeTracker> carTypeTracker = carTypeTrackerDao.findByCarType(carType);
        return carTypeTracker.map(CarTypeAdapter::toCarTypeTrackerDTO).orElseThrow(() -> new RuntimeException("Car Type not found"));
    }
}
