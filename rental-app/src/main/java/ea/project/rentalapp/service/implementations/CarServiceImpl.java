package ea.project.rentalapp.service.implementations;

import ea.project.rentalapp.service.CarService;
import ea.project.rentalapp.service.configuration.CarFleetProperties;
import ea.project.rentalapp.service.dto.CarDto;
import ea.project.rentalapp.service.dto.ReservationDto;
;
import ea.project.rentalapp.service.jms.ReservationJmsSender;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CarServiceImpl implements CarService {


    private final ReservationJmsSender reservationJmsSender;
    private final RestTemplate restTemplate;

    private String FLEET_URL;

    private final CarFleetProperties carFleetProperties;


    public CarServiceImpl(RestTemplate restTemplate, CarFleetProperties carFleetProperties, ReservationJmsSender reservationJmsSender) {
        this.restTemplate = restTemplate;
        this.carFleetProperties = carFleetProperties;
        this.reservationJmsSender = reservationJmsSender;
    }


    @PostConstruct
    public void init() {
        FLEET_URL = carFleetProperties.getUrl() + "/cars";
    }


    @Override
    public List<CarDto> findAll() {
        ResponseEntity<List<CarDto>> carResponse =
                restTemplate.exchange(FLEET_URL,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<CarDto>>() {
                        });
        return carResponse.getBody();
    }

    @Override
    public CarDto findById(String id) {
        return restTemplate.getForObject(FLEET_URL + "/" + id, CarDto.class);
    }

    @Override
    public CarDto save(CarDto carDto) {
        return restTemplate.postForObject(FLEET_URL, carDto, CarDto.class);
    }

    @Override
    public CarDto update(String id, CarDto carDto) {
        restTemplate.put(FLEET_URL + "/" + id, carDto);
        return findById(id);
    }

    @Override
    public List<CarDto> findByBrand(String brand) {
        ResponseEntity<List<CarDto>> carResponse =
                restTemplate.exchange(FLEET_URL + "/search?by=brand&value=" + brand,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<CarDto>>() {
                        });
        return carResponse.getBody();

    }

    @Override
    public List<CarDto> findAvailableCarsByType(String carType) {
        ResponseEntity<List<CarDto>> carResponse =
                restTemplate.exchange(FLEET_URL + "/search?by=type&value=" + carType,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<CarDto>>() {
                        });
        return carResponse.getBody();
    }

    @Override
    public List<CarDto> findAvailableCars() {
        ResponseEntity<List<CarDto>> carResponse =
                restTemplate.exchange(FLEET_URL + "/search?by=rental-status&value=available",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<CarDto>>() {
                        });
        return carResponse.getBody();
    }

    @Override
    public CarDto findByLicensePlate(String licensePlate) {
        return restTemplate.getForObject(FLEET_URL + "/search?by=license-plate&value=" + licensePlate, CarDto.class);
    }

    @Override
    public void delete(String id) {
        restTemplate.delete(FLEET_URL + "/" + id);
    }

    @Override
    public boolean rentCar(String carId, ReservationDto reservationDto) {
        try {
            reservationJmsSender.send("reserveQueue", reservationDto);
            checkAvailability();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean returnCar(String carId, ReservationDto reservationDto) {
        try {
            reservationJmsSender.send("returnQueue", reservationDto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<CarDto> findRentedCars() {
        return restTemplate.getForObject(FLEET_URL + "/search?by=rental-status&value=rented", List.class);
    }

    @Override
    public void checkAvailability() {
        List<CarDto> cars = findAll();
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getRentalStatus().equals("AVAILABLE")) {
                System.out.println(cars.get(i));
            }

        }
    }
}
