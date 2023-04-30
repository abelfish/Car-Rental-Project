package ea.project.carfleet.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private String reservationId;
    private String customerNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
