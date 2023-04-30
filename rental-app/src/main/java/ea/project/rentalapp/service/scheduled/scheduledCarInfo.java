package ea.project.rentalapp.service.scheduled;


import ea.project.rentalapp.service.CarService;
import ea.project.rentalapp.service.dto.CarDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class scheduledCarInfo {

    private final CarService carService;

    public scheduledCarInfo(CarService carService) {
        this.carService = carService;
    }

    @Scheduled(fixedRate = 20 * 1000)
    public void getCarInfo() {
        System.out.println("\n-----------------------------------------------------------------------------------------------------------");
        System.out.println("Getting car info");
        List<CarDto> cars = carService.findAll();
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.println("|\tBRAND\t->\tMODEL\t->\tLICENSE PLATE\t->\tTYPE\t->\tRENTAL STATUS\t->\tPRICE PER DAY\t|");
        cars.forEach(carDto -> {
            String carString = String.format("|\t%s\t->\t%s\t->\t$s\t->\t%s\t->\t%s\t->\t%s\t->\t%s\t|", carDto.getBrand(), carDto.getModel(), carDto.getLicensePlate()
                    , carDto.getCarType(), carDto.getRentalStatus(), carDto.getPricePerDay().toString());
            System.out.println(carString);
        });
        System.out.println("---------------------------------------------------------------------------------------------------------\n");


    }
}
