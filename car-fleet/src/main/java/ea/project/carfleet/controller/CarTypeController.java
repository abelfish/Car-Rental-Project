package ea.project.carfleet.controller;

import ea.project.carfleet.domain.CarType;
import ea.project.carfleet.service.CarTypeTrackerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car-type")
public class CarTypeController {
    private final CarTypeTrackerService carTypeTrackerService;
    public CarTypeController(CarTypeTrackerService carTypeTrackerService) {
        this.carTypeTrackerService = carTypeTrackerService;

    }

    @GetMapping
    public ResponseEntity<?> getAllCarTypes() {
        return new ResponseEntity<>(carTypeTrackerService.getAllCarTypes(), null, 200);
    }
    @GetMapping("/{carType}")
    public ResponseEntity<?> getCarType(@PathVariable String carType) {
        return new ResponseEntity<>(carTypeTrackerService.getCarType(CarType.valueOf(carType.toUpperCase())), null, 200);
    }
}
