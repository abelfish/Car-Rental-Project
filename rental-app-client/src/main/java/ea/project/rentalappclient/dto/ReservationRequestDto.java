package ea.project.rentalappclient.dto;

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
    private String rentalStatus;
    private String carId;
}
