package ea.project.rentalappclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RentalAppClientApplication implements CommandLineRunner {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String FLEET_URL = "http://localhost:8090/";

    public static void main(String[] args) {
        SpringApplication.run(RentalAppClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("------------------------------------------------------------------------");
        EmployeeClient.run();
    }

}
