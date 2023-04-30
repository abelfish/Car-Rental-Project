package ea.project.rentalapp.service.scheduled;

import ea.project.rentalapp.service.CarService;
import ea.project.rentalapp.service.dto.CarDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data

public class AvailabilityChecker {

    private final CarService carService;

    public AvailabilityChecker(CarService carService) {
        this.carService = carService;
    }
    public void checkAvailability(){
        List<CarDto> cars = carService.findAll();
        cars.stream().filter(carDto -> carDto.getRentalStatus().equals("AVAILABLE")).forEach(System.out::println);
    }
}
