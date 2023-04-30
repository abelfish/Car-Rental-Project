package ea.project.carfleet.domain;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "cars")
public class Car {
    @Id
    private String id;
    private String brand;
    private String model;
    //unique
    @Indexed(unique = true)
    private String licensePlate;
    private CarType carType;
    private RentalStatus rentalStatus;
    private double pricePerDay;
    private List<Reservation> reservations;

}
