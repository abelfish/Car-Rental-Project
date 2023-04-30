package ea.project.fleetclient.dto;

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
    private String carId;
}
