package ea.project.rentalappclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private long reservationId;
    private LocalDateTime reservationDate;
    private LocalDateTime returnDate;
    private PaymentInfoDto paymentInfoDto;
    private String reservationStatus;
    private String carId;
    private EmployeeDto bookingEmployeeDto;
    private EmployeeDto returnEmployeeDto;
}
