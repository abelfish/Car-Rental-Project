package ea.project.carfleet.service.dto;


import ea.project.carfleet.domain.CarType;
import ea.project.carfleet.domain.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarDTO {
    private String id;
    private String brand;
    private String model;
    private String licensePlate;
    private CarType carType;
    private RentalStatus rentalStatus;
    private Double pricePerDay;
}
