package ea.project.carfleet.service.adapters;

import ea.project.carfleet.domain.Car;
import ea.project.carfleet.service.dto.CarDTO;

import java.util.List;

public class CarAdapter {

    public static Car fromCarDtoToCar(CarDTO carDto) {
        Car car = new Car();
        car.setId(carDto.getId());
        car.setCarType(carDto.getCarType());
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setLicensePlate(carDto.getLicensePlate());
        car.setRentalStatus(carDto.getRentalStatus());
        car.setPricePerDay(carDto.getPricePerDay());

        return car;

    }
    public static CarDTO fromCarToCarDto(Car car) {
        CarDTO carDto = new CarDTO();
        carDto.setId(car.getId());
        carDto.setCarType(car.getCarType());
        carDto.setBrand(car.getBrand());
        carDto.setModel(car.getModel());
        carDto.setLicensePlate(car.getLicensePlate());
        carDto.setRentalStatus(car.getRentalStatus());
        carDto.setPricePerDay(car.getPricePerDay());
        return carDto;
    }

    public static List<CarDTO> fromCarListToCarDtoList(List<Car> cars) {
        return cars.stream().map(CarAdapter::fromCarToCarDto).toList();
    }
}
