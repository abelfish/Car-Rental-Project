package ea.project.rentalapp.dao;

import ea.project.rentalapp.domain.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInfoDao extends JpaRepository<PaymentInfo, Long> {
}
