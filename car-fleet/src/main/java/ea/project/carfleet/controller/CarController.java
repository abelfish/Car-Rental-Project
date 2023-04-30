package ea.project.carfleet.controller;

import ea.project.carfleet.service.CarService;
import ea.project.carfleet.service.CarTypeTrackerService;
import ea.project.carfleet.service.RentalStatusTrackerService;
import ea.project.carfleet.service.dto.CarDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    private final CarTypeTrackerService carTypeTrackerService;

    private final RentalStatusTrackerService rentalStatusTrackerService;

    public CarController(CarService carService, CarTypeTrackerService carTypeTrackerService,
            RentalStatusTrackerService rentalStatusTrackerService) {
        this.carService = carService;
        this.carTypeTrackerService = carTypeTrackerService;
        this.rentalStatusTrackerService = rentalStatusTrackerService;

    }

    @GetMapping
    public ResponseEntity<?> getAllCars() {
        return new ResponseEntity<>(carService.getAllCars(), null, 200);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarById(@PathVariable String id) {
        return new ResponseEntity<>(carService.getCarById(id), null, 200);
    }

    @PostMapping
    public ResponseEntity<?> addCar(@RequestBody CarDTO carDTO) {
        return new ResponseEntity<>(carService.addCar(carDTO), null, 200);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable String id, @RequestBody CarDTO carDTO) {
        return new ResponseEntity<>(carService.updateCar(id, carDTO), null, 200);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable String id) {
        return new ResponseEntity<>(carService.deleteCar(id), null, 200);
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<?> getCarsByBrand(@PathVariable String brand) {
        return new ResponseEntity<>(carService.getCarsByBrand(brand), null, 200);
    }

    @GetMapping("/available/{carType}")
    public ResponseEntity<?> getCarsByRentalStatusAndCarType(@PathVariable String carType) {
        return new ResponseEntity<>(carService.getCarsByRentalStatusAndCarType("AVAILABLE", carType.toUpperCase()),
                null, 200);
    }

    @GetMapping("/priceGreaterThan/{greaterThan}")
    public ResponseEntity<?> getCarsByPricePerDayGreaterThanEqual(@PathVariable double greaterThan) {
        return new ResponseEntity<>(carService.getCarsByPricePerDayGreaterThanEqual(greaterThan), null, 200);
    }

    @GetMapping("/priceLessThan/{lessThan}")
    public ResponseEntity<?> getCarsByPricePerDayLessThanEqual(@PathVariable double lessThan) {
        return new ResponseEntity<>(carService.getCarsByPricePerDayLessThanEqual(lessThan), null, 200);
    }

    @GetMapping("/license-plate/{licensePlate}")
    public ResponseEntity<?> getCarsByLicensePlate(@PathVariable String licensePlate) {
        return new ResponseEntity<>(carService.getCarsByLicensePlate(licensePlate), null, 200);
    }

    @GetMapping("/{rentalStatus}")
    public ResponseEntity<?> getRentedCars(@PathVariable String rentalStatus) {
        return new ResponseEntity<>(carService.getAllCarsByRentalStatus(rentalStatus.toUpperCase()), null, 200);
    }

}
