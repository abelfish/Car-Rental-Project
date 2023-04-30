package ea.project.rentalapp.domain;

import ea.project.rentalapp.domain.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static javax.persistence.EnumType.STRING;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class PaymentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId;
    private String creditCardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String securityCode;
    @Enumerated(STRING)
    private PaymentStatus paymentStatus;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
}
