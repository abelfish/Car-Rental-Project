package ea.project.carfleet.service.dto;

import ea.project.carfleet.domain.RentalStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RentalStatusTrackerDTO {
    private RentalStatus rentalStatus;
    private int count;
}
