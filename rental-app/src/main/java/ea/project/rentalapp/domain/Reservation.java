package ea.project.rentalapp.domain;

import ea.project.rentalapp.domain.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import static javax.persistence.EnumType.STRING;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationId;
    private LocalDateTime reservationDate;
    private LocalDateTime returnDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private PaymentInfo paymentInfo;
    @Enumerated(STRING)
    private ReservationStatus reservationStatus;
    private String carId;
    @ManyToOne
    private Employee bookingEmployee;
    @ManyToOne
    private Employee returnEmployee;
}
