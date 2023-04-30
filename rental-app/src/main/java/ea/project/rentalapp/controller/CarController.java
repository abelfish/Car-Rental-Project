package ea.project.rentalapp.controller;

import ea.project.rentalapp.service.CarService;
import ea.project.rentalapp.service.dto.CarDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(carService.findAll());
    }

    @GetMapping("/available")
    public ResponseEntity<?> findAvailableCars() {
        return ResponseEntity.ok(carService.findAvailableCars());
    }

    @GetMapping("/available/{carType}")
    public ResponseEntity<?> findAvailableCarsByType(@PathVariable String carType) {
        return ResponseEntity.ok(carService.findAvailableCarsByType(carType));
    }
    @GetMapping("/rented")
    public ResponseEntity<?> findRentedCars() {
        return ResponseEntity.ok(carService.findRentedCars());
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<?> findByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(carService.findByBrand(brand));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok(carService.findById(id));
    }

    @GetMapping("/licensePlate/{licensePlate}")
    public ResponseEntity<?> findByLicensePlate(@PathVariable String licensePlate) {
        return ResponseEntity.ok(carService.findByLicensePlate(licensePlate));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CarDto carDto) {
        return ResponseEntity.ok(carService.save(carDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody CarDto carDto) {
        return ResponseEntity.ok(carService.update(id, carDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        carService.delete(id);
        return ResponseEntity.ok("Car deleted successfully");
    }
}
