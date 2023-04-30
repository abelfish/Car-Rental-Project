package ea.project.fleetclient.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CarTypeTrackerDTO {
    private String carType;
    private int count;
}
