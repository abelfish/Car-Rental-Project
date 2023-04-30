package ea.project.carfleet.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "car-type-tracker")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarTypeTracker {
    @Id
    private String id;
    private CarType carType;
    private int count;
}
