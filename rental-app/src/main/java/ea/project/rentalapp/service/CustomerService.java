package ea.project.rentalapp.service;

import ea.project.rentalapp.service.dto.CustomerDto;
import ea.project.rentalapp.service.dto.ReservationDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> findAll();
    CustomerDto findById(Long id);
    CustomerDto save(CustomerDto customerDto);
    CustomerDto update(Long id,CustomerDto customerDto);
    void delete(Long id);
    CustomerDto findByCustomerNumber(String customerNumber);
    CustomerDto findByEmail(String email);
    List<CustomerDto> findByName(String name);

    ReservationDto reserveCar(Long customerId, ReservationDto reservationDto);
    ReservationDto rentCar(long customerId,long reservationId);
    ReservationDto returnCar(Long customerId, ReservationDto reservationDto);


    List<ReservationDto> getRentedCars(long customerId);
}
