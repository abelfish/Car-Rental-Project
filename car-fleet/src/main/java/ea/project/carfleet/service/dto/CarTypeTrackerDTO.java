package ea.project.carfleet.service.dto;

import ea.project.carfleet.domain.CarType;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CarTypeTrackerDTO {
    private CarType carType;
    private int count;
}
