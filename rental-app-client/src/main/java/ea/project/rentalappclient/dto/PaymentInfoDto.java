package ea.project.rentalappclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentInfoDto {
    private long paymentId;
    private String creditCardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String securityCode;
    private String paymentStatus;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
}
