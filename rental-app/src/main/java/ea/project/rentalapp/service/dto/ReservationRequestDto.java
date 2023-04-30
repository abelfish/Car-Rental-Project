package ea.project.rentalapp.service.dto;

import ea.project.rentalapp.domain.enums.ReservationStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class ReservationRequestDto {
    private long reservationId;
    private LocalDateTime reservationDate;
    private LocalDateTime returnDate;
    private PaymentInfoDto paymentInfo;
    private ReservationStatus rentalStatus;
    private String carId;
}
