package ea.project.rentalapp.controller;

import ea.project.rentalapp.service.EmployeeService;
import ea.project.rentalapp.service.dto.EmployeeDto;
import ea.project.rentalapp.service.dto.ReservationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }
    @GetMapping("/{id}/reservations")
    public ResponseEntity<?> getEmployeeReservations(@PathVariable long id) {
        return ResponseEntity.ok(employeeService.getEmployeeReservations(id));
    }
    @PostMapping
    public ResponseEntity<?> save(@RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.ok(employeeService.save(employeeDto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.ok(employeeService.update(id, employeeDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        employeeService.delete(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/makeReservation/{customerId}")
    public ResponseEntity<?> makeReservation(@PathVariable long id, @PathVariable long customerId, @RequestBody ReservationDto reservationDto) {
        return ResponseEntity.ok(employeeService.makeReservation(id,customerId, reservationDto));
    }
    @PostMapping("/{id}/returnCar")
    public ResponseEntity<?> returnCar(@PathVariable long id, @RequestBody ReservationDto reservationDto) {
        return ResponseEntity.ok(employeeService.returnCar(id, reservationDto));
    }

}
