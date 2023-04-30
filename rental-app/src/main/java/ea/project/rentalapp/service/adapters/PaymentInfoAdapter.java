package ea.project.rentalapp.service.adapters;

import ea.project.rentalapp.domain.PaymentInfo;
import ea.project.rentalapp.service.dto.PaymentInfoDto;

public class PaymentInfoAdapter {
    public static PaymentInfoDto toDto(PaymentInfo paymentInfo) {
        PaymentInfoDto paymentInfoDto = new PaymentInfoDto();
        paymentInfoDto.setPaymentId(paymentInfo.getPaymentId());
        paymentInfoDto.setCreditCardNumber(paymentInfo.getCreditCardNumber());
        paymentInfoDto.setCardHolderName(paymentInfo.getCardHolderName());
        paymentInfoDto.setExpirationDate(paymentInfo.getExpirationDate());
        paymentInfoDto.setSecurityCode(paymentInfo.getSecurityCode());
        paymentInfoDto.setPaymentStatus(paymentInfo.getPaymentStatus());
        paymentInfoDto.setAmount(paymentInfo.getAmount());
        paymentInfoDto.setPaymentDate(paymentInfo.getPaymentDate());
        return paymentInfoDto;
    }

    public static PaymentInfo toPaymentInfo(PaymentInfoDto paymentInfoDto) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setPaymentId(paymentInfoDto.getPaymentId());
        paymentInfo.setCreditCardNumber(paymentInfoDto.getCreditCardNumber());
        paymentInfo.setCardHolderName(paymentInfoDto.getCardHolderName());
        paymentInfo.setExpirationDate(paymentInfoDto.getExpirationDate());
        paymentInfo.setSecurityCode(paymentInfoDto.getSecurityCode());
        paymentInfo.setPaymentStatus(paymentInfoDto.getPaymentStatus());
        paymentInfo.setAmount(paymentInfoDto.getAmount());
        paymentInfo.setPaymentDate(paymentInfoDto.getPaymentDate());
        return paymentInfo;
    }
}
