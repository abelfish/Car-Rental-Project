package ea.project.carfleet.controller;


import ea.project.carfleet.domain.RentalStatus;
import ea.project.carfleet.service.RentalStatusTrackerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rental-status")
public class RentalStatusController {

    private final RentalStatusTrackerService rentalStatusTrackerService;

    public RentalStatusController(RentalStatusTrackerService rentalStatusTrackerService) {
        this.rentalStatusTrackerService = rentalStatusTrackerService;
    }

    @GetMapping
    public ResponseEntity<?> getAllRentalStatus() {
        return new ResponseEntity<>(rentalStatusTrackerService.getAllRentalStatus(), null, 200);
    }

    @GetMapping("/{status}")
    public ResponseEntity<?> getAvailableRentalStatus(@PathVariable String status) {
        return new ResponseEntity<>(rentalStatusTrackerService.getRentalStatus(RentalStatus.valueOf(status.toUpperCase())), null, 200);
    }

}
