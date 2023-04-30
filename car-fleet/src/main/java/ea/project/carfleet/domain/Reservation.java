package ea.project.carfleet.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reservation {
    private String reservationId;
    private String customerNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
