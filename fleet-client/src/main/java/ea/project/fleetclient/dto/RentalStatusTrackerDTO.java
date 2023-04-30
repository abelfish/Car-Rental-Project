package ea.project.fleetclient.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RentalStatusTrackerDTO {
    private String rentalStatus;
    private int count;
}
