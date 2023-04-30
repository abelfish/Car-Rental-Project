package ea.project.fleetclient.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarDTO {
    private String id;
    private String brand;
    private String model;
    private String licensePlate;
    private String carType;
    private String rentalStatus;
    private BigDecimal pricePerDay;
    private List<ReservationDto> reservationDtoList;
}
