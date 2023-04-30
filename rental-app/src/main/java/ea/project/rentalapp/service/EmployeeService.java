package ea.project.rentalapp.service;

import ea.project.rentalapp.service.dto.EmployeeDto;
import ea.project.rentalapp.service.dto.ReservationDto;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> findAll();
    EmployeeDto findById(long id);
    EmployeeDto save(EmployeeDto employeeDto);
    EmployeeDto update(long id,EmployeeDto employeeDto);
    void delete(long id);

    ReservationDto makeReservation(long id,long customerId,ReservationDto reservationDto);
    ReservationDto returnCar(long id,ReservationDto reservationDto);

    List<ReservationDto> getEmployeeReservations(long id);


}
