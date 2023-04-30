package ea.project.carfleet.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rental-status-tracker")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentalStatusTracker {
    @Id
    private String id;
    private RentalStatus rentalStatus;
    private int count;

}
