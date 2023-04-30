package ea.project.rentalapp.service.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "app.car-fleet")
public class CarFleetProperties {
    private String url;
    private int maxRental;
}
