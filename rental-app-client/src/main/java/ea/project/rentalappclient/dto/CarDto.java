package ea.project.rentalappclient.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarDto {
    private String id;
    private String brand;
    private String model;
    private String licensePlate;
    private String carType;
    private String rentalStatus;
    private BigDecimal pricePerDay;


}
