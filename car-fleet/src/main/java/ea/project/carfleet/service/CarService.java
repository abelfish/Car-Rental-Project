package ea.project.carfleet.service;

import ea.project.carfleet.service.dto.CarDTO;

import java.util.List;

public interface CarService {
    List<CarDTO> getAllCars();
    CarDTO getCarById(String id);
    CarDTO addCar(CarDTO carDTO);
    CarDTO updateCar(String id,CarDTO carDTO);
    boolean deleteCar(String id);
    List<CarDTO> getCarsByBrand(String brand);
    List<CarDTO> getCarsByRentalStatusAndCarType(String rentalStatus, String carType);
    List<CarDTO> getCarsByPricePerDayGreaterThanEqual(double pricePerDay);
    List<CarDTO> getCarsByPricePerDayLessThanEqual(double pricePerDay);
    CarDTO getCarsByLicensePlate(String licensePlate);
    List<CarDTO> getAllCarsByRentalStatus(String rented);

    boolean rentCar(String id);
    boolean returnCar(String id);
}
