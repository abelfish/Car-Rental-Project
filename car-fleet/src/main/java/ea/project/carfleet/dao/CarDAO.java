package ea.project.carfleet.dao;

import ea.project.carfleet.domain.Car;
import ea.project.carfleet.domain.CarType;
import ea.project.carfleet.domain.RentalStatus;
import ea.project.carfleet.service.dto.CarDTO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CarDAO extends MongoRepository<Car, String> {
    List<Car> findAllByBrand(String brand);
    List<Car> findAllByRentalStatusAndCarType(RentalStatus rentalStatus, CarType carType);
    List<Car> findAllByPricePerDayGreaterThanEqual(double pricePerDay);
    List<Car> findAllByPricePerDayLessThanEqual(double pricePerDay);
    Car findCarByLicensePlate(String licensePlate);

    List<Car> findAllByRentalStatus(RentalStatus rentalStatus);
}
