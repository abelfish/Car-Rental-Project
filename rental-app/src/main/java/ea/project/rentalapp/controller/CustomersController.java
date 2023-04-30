package ea.project.rentalapp.controller;

import ea.project.rentalapp.service.CustomerService;
import ea.project.rentalapp.service.dto.CustomerDto;
import ea.project.rentalapp.service.dto.ReservationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomersController {
    private final CustomerService customerService;

    public CustomersController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<?> getCustomers() {
        List<CustomerDto> customers = customerService.findAll();
        return ResponseEntity.ok(customers);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        CustomerDto customer = customerService.findById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getCustomerByCustomerNumber(@RequestParam String by, @RequestParam String value) {
        return switch (by) {
            case "customerNumber" -> ResponseEntity.ok(customerService.findByCustomerNumber(value));
            case "email" -> ResponseEntity.ok(customerService.findByEmail(value));
            case "name" -> ResponseEntity.ok(customerService.findByName(value));
            default -> ResponseEntity.badRequest().body("Invalid search parameter");
        };
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable long id, @RequestBody CustomerDto customerDto) {
        CustomerDto customer = customerService.update(id, customerDto);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<?> saveCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto customer = customerService.save(customerDto);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.ok("Customer deleted");
    }

    @PostMapping("{customerId}/rent/{reservationId}")
    public ResponseEntity<?> rentCar(@PathVariable long customerId, @PathVariable long reservationId) {
        return ResponseEntity.ok(customerService.rentCar(customerId, reservationId));
    }

    @PostMapping("/{customerId}/reserve")
    public ResponseEntity<?> reserveCar(@PathVariable Long customerId, @RequestBody ReservationDto reservationDto) {
        ReservationDto reservation = customerService.reserveCar(customerId, reservationDto);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/{customerId}/return")
    public ResponseEntity<?> returnCar(@PathVariable Long customerId, @RequestBody ReservationDto ReservationDto) {
        ReservationDto reservation = customerService.returnCar(customerId, ReservationDto);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/{customerId}/rented")
    public ResponseEntity<?> getRentedCars(@PathVariable Long customerId) {
        List<ReservationDto> reservations = customerService.getRentedCars(customerId);
        return ResponseEntity.ok(reservations);
    }
}
