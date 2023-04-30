package ea.project.rentalapp;

import ea.project.rentalapp.service.CarService;
import ea.project.rentalapp.service.configuration.CarFleetProperties;
import ea.project.rentalapp.service.configuration.Config;
import ea.project.rentalapp.service.implementations.CarServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJms
@EnableConfigurationProperties(CarFleetProperties.class)
public class RentalAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentalAppApplication.class, args);
    }

}
