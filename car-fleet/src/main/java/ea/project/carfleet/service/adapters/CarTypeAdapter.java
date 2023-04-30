package ea.project.carfleet.service.adapters;

import ea.project.carfleet.domain.CarTypeTracker;
import ea.project.carfleet.service.dto.CarTypeTrackerDTO;

import java.util.List;

public class CarTypeAdapter {
    public static CarTypeTrackerDTO toCarTypeTrackerDTO(CarTypeTracker carTypeTracker) {
        CarTypeTrackerDTO carTypeTrackerDTO = new CarTypeTrackerDTO();
        carTypeTrackerDTO.setCarType(carTypeTracker.getCarType());
        carTypeTrackerDTO.setCount(carTypeTracker.getCount());
        return carTypeTrackerDTO;
    }
    public static CarTypeTracker toCarTypeTracker(CarTypeTrackerDTO carTypeTrackerDTO) {
        CarTypeTracker carTypeTracker = new CarTypeTracker();
        carTypeTracker.setCarType(carTypeTrackerDTO.getCarType());
        carTypeTracker.setCount(carTypeTrackerDTO.getCount());
        return carTypeTracker;
    }
    public static List<CarTypeTrackerDTO> toCarTypeTrackerDTOList(List<CarTypeTracker> carTypeTrackerList) {
        return carTypeTrackerList.stream().map(CarTypeAdapter::toCarTypeTrackerDTO).toList();
    }
}
