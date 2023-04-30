package ea.project.fleetclient;

import ea.project.fleetclient.dto.CarDTO;
import ea.project.fleetclient.dto.ReservationDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class FleetClientApplication implements CommandLineRunner {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String FLEET_URL = "http://localhost:8080/";

    public static void main(String[] args) {
        SpringApplication.run(FleetClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Get All Cars");
        System.out.println(restTemplate.getForEntity(FLEET_URL + "/cars", List.class));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Post One Car AND Get CAR");
        CarDTO carDTO = new CarDTO();
        carDTO.setCarType("PICKUP");
        carDTO.setBrand("FORD");
        carDTO.setModel("F-150");
        carDTO.setLicensePlate(String.valueOf(new Random().nextInt(10000, 99999)));
        carDTO.setPricePerDay(BigDecimal.valueOf(new Random().nextDouble(30, 100)));
        carDTO = restTemplate.postForObject(FLEET_URL + "/cars", carDTO, CarDTO.class);
        assert carDTO != null;
        System.out.println(restTemplate.getForObject(FLEET_URL + "/cars/" + carDTO.getId(), CarDTO.class));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Update Car");
        carDTO.setPricePerDay(BigDecimal.valueOf(new Random().nextDouble(30, 100)));
        restTemplate.put(FLEET_URL + "/cars/" + carDTO.getId(), carDTO);
        System.out.println(restTemplate.getForObject(FLEET_URL + "/cars/" + carDTO.getId(), CarDTO.class));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Delete Car");
        restTemplate.delete(FLEET_URL + "/cars/" + carDTO.getId());
        System.out.println(restTemplate.getForEntity(FLEET_URL + "/cars", List.class));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("search cars by brand TOYOTA");
        System.out.println(restTemplate.getForEntity(FLEET_URL + "/cars/search?by=brand&value=TOYOTA", List.class));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("search cars by type SUV");
        System.out.println(restTemplate.getForEntity(FLEET_URL + "/cars/search?by=car-type&value=SUV", List.class));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Get all available cars");
        System.out.println(restTemplate.getForEntity(FLEET_URL + "/cars/search?by=rental-status&value=available", List.class));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Get rental status tracker Tracker");
        System.out.println(restTemplate.getForEntity(FLEET_URL + "/rental-status", List.class));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Get Car Type Tracker");
        System.out.println(restTemplate.getForEntity(FLEET_URL + "/car-type", List.class));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Get all Rented Cars");
        List<CarDTO> rentedCars = restTemplate.getForObject(FLEET_URL + "/cars/search?by=rental-status&value=rented", List.class);
        System.out.println(rentedCars);
        System.out.println("------------------------------------------------------------------------");
        System.exit(0);

    }
}
