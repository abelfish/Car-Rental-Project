package ea.project.carfleet.service.implementations;

import ea.project.carfleet.dao.CarDAO;
import ea.project.carfleet.domain.Car;
import ea.project.carfleet.domain.CarType;
import ea.project.carfleet.domain.RentalStatus;
import ea.project.carfleet.service.CarService;
import ea.project.carfleet.service.CarTypeTrackerService;
import ea.project.carfleet.service.RentalStatusTrackerService;
import ea.project.carfleet.service.adapters.CarAdapter;
import ea.project.carfleet.service.dto.CarDTO;
import ea.project.carfleet.service.exceptions.CarNotFoundException;
import ea.project.carfleet.service.exceptions.InvalidInputException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CarServiceImpl implements CarService {

    private final CarDAO carDAO;
    private final RentalStatusTrackerService rentalTrackerService;
    private final CarTypeTrackerService carTypeService;

    public CarServiceImpl(CarDAO carDAO, RentalStatusTrackerService rentalTrackerService, CarTypeTrackerService carTypeService) {
        this.carDAO = carDAO;
        this.rentalTrackerService = rentalTrackerService;
        this.carTypeService = carTypeService;

    }


    @Override
    public List<CarDTO> getAllCars() {
        return CarAdapter.fromCarListToCarDtoList(carDAO.findAll());
    }

    @Override
    public CarDTO getCarById(String id) {
        //convert int to Character
        return CarAdapter.fromCarToCarDto(carDAO.findById(id).orElseThrow(() -> new CarNotFoundException("Car with id: " + id + " not found")));
    }

    @Override
    public CarDTO addCar(CarDTO carDTO) {

        Car car = CarAdapter.fromCarDtoToCar(carDTO);

        Car lookupLicensePlate = carDAO.findCarByLicensePlate(car.getLicensePlate());
        if (lookupLicensePlate != null) {
            throw new RuntimeException("Car with license plate: " + car.getLicensePlate() + " already exists");
        }
        rentalTrackerService.incrementRentalStatusTracker(car.getRentalStatus());
        carTypeService.incrementCarTypeTracker(car.getCarType());

        return CarAdapter.fromCarToCarDto(carDAO.save(car));
    }

    @Override
    public CarDTO updateCar(String id, CarDTO carDTO) {
        Car car = carDAO.findById(id).orElseThrow(() -> new CarNotFoundException("Car with id: " + id + " not found"));

        if (carDTO.getCarType() != null && !carDTO.getCarType().equals(car.getCarType())) {
            throw new InvalidInputException("Car type cannot be changed");
        }
        if (carDTO.getRentalStatus() != null && !carDTO.getRentalStatus().equals(car.getRentalStatus())) {
            throw new InvalidInputException("Rental status cannot be changed");
        }

        //assert attributes of carDTO are not null
        car.setBrand(Optional.ofNullable(carDTO.getBrand()).orElse(car.getBrand()));
        car.setCarType(Optional.ofNullable(carDTO.getCarType()).orElse(car.getCarType()));
        car.setModel(Optional.ofNullable(carDTO.getModel()).orElse(car.getModel()));
        car.setLicensePlate(Optional.ofNullable(carDTO.getLicensePlate()).orElse(car.getLicensePlate()));
        car.setRentalStatus(Optional.ofNullable(carDTO.getRentalStatus()).orElse(car.getRentalStatus()));
        car.setPricePerDay(Optional.ofNullable(carDTO.getPricePerDay()).orElse(car.getPricePerDay()));
        return CarAdapter.fromCarToCarDto(carDAO.save(car));
    }

    @Override
    public boolean deleteCar(String id) {
        Car car = carDAO.findById(id).orElseThrow(() -> new CarNotFoundException("Car with id: " + id + " not found"));
        carDAO.delete(car);
        carTypeService.decrementCarTypeTracker(car.getCarType());
        rentalTrackerService.decrementRentalStatusTracker(car.getRentalStatus());
        return true;
    }

    @Override
    public List<CarDTO> getCarsByBrand(String brand) {
        return CarAdapter.fromCarListToCarDtoList(carDAO.findAllByBrand(brand));
    }

    @Override
    public List<CarDTO> getCarsByRentalStatusAndCarType(String status, String type) {
        try {
            RentalStatus rentalStatus = RentalStatus.valueOf(status);
            CarType carType = CarType.valueOf(type);
            return CarAdapter.fromCarListToCarDtoList(carDAO.findAllByRentalStatusAndCarType(rentalStatus, carType));
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid rental status or car type");
        }
    }

    @Override
    public List<CarDTO> getCarsByPricePerDayGreaterThanEqual(double pricePerDay) {
        if (pricePerDay <= 0) {
            throw new InvalidInputException("Price per day cannot be negative or zero");
        }
        return CarAdapter.fromCarListToCarDtoList(carDAO.findAllByPricePerDayGreaterThanEqual(pricePerDay));
    }

    @Override
    public List<CarDTO> getCarsByPricePerDayLessThanEqual(double pricePerDay) {
        if (pricePerDay <= 0) {
            throw new InvalidInputException("Price per day cannot be negative or zero");
        }
        return CarAdapter.fromCarListToCarDtoList(carDAO.findAllByPricePerDayLessThanEqual(pricePerDay));
    }

    @Override
    public CarDTO getCarsByLicensePlate(String licensePlate) {
        return CarAdapter.fromCarToCarDto(carDAO.findCarByLicensePlate(licensePlate));
    }

    @Override
    public List<CarDTO> getAllCarsByRentalStatus(String status) {
        try {
            RentalStatus rentalStatus = RentalStatus.valueOf(status.toUpperCase());
            return CarAdapter.fromCarListToCarDtoList(carDAO.findAllByRentalStatus(rentalStatus));
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid rental status");
        }
    }

    @Override
    public boolean rentCar(String id) {
        Car car = carDAO.findById(id).orElseThrow(() -> new CarNotFoundException("Car with id: " + id + " not found"));
        if (car.getRentalStatus().equals(RentalStatus.RENTED)) {
            throw new RuntimeException("Car with id: " + id + " is already rented");
        }
        car.setRentalStatus(RentalStatus.RENTED);
        rentalTrackerService.incrementRentalStatusTracker(RentalStatus.RENTED);
        rentalTrackerService.decrementRentalStatusTracker(RentalStatus.AVAILABLE);
        carDAO.save(car);
        return true;
    }

    @Override
    public boolean returnCar(String id) {
        Car car = carDAO.findById(id).orElseThrow(() -> new CarNotFoundException("Car with id: " + id + " not found"));
        if (car.getRentalStatus().equals(RentalStatus.AVAILABLE)) {
            throw new RuntimeException("Car with id: " + id + " is already available");
        }
        car.setRentalStatus(RentalStatus.AVAILABLE);
        rentalTrackerService.incrementRentalStatusTracker(RentalStatus.AVAILABLE);
        rentalTrackerService.decrementRentalStatusTracker(RentalStatus.RENTED);
        carDAO.save(car);
        return true;
    }
}
