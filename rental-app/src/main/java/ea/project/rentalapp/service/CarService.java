package ea.project.rentalapp.service;

import ea.project.rentalapp.service.dto.CarDto;
import ea.project.rentalapp.service.dto.ReservationDto;
import java.util.List;

public interface CarService {
    List findAll();
    CarDto findById(String id);
    CarDto save(CarDto carDto);
    CarDto update(String id, CarDto carDto);
    List<CarDto> findByBrand(String brand);
    List<CarDto> findAvailableCarsByType(String carType);
    List<CarDto> findAvailableCars();
    CarDto findByLicensePlate(String licensePlate);
    void delete(String id);
    boolean rentCar(String carId, ReservationDto reservationDto);
    boolean returnCar(String carId, ReservationDto reservationDto);
    List<CarDto> findRentedCars();
    void checkAvailability();
}
