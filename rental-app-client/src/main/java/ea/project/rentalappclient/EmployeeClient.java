package ea.project.rentalappclient;

import ea.project.rentalappclient.dto.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class EmployeeClient {
    public static void run() {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("ADD EMPLOYEE");
        System.out.println("------------------------------------------------------------------------");
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName("John Doe");
        employeeDto.setEmail("email@email.com");
        employeeDto = restTemplate.postForObject("http://localhost:8090/employees", employeeDto, EmployeeDto.class);
        System.out.println(employeeDto);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------");
        System.out.println("EmployeeClient");
        System.out.println("------------------------------------------------------------------------");
        System.out.println("GET ALL EMPLOYEES");
        System.out.println("------------------------------------------------------------------------");
        System.out.println(restTemplate.getForObject("http://localhost:8090/employees", String.class));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("GET EMPLOYEE BY ID");
        System.out.println("------------------------------------------------------------------------");
        System.out.println(restTemplate.getForObject("http://localhost:8090/employees/" + employeeDto.getEmployeeId(), String.class));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("UPDATE EMPLOYEE");
        System.out.println("------------------------------------------------------------------------");
        employeeDto.setName("Jane Doe");
        restTemplate.put("http://localhost:8090/employees/" + employeeDto.getEmployeeId(), employeeDto, String.class);
        System.out.println(restTemplate.getForObject("http://localhost:8090/employees/"+employeeDto.getEmployeeId(), EmployeeDto.class));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("DELETE EMPLOYEE");
        System.out.println("------------------------------------------------------------------------");
        restTemplate.delete("http://localhost:8090/employees/" + employeeDto.getEmployeeId());
        System.out.println(restTemplate.getForObject("http://localhost:8090/employees", List.class));

        System.out.println("------------------------------------------------------------------------");
        System.out.println("Make a reservation");
        System.out.println("------------------------------------------------------------------------");
        employeeDto = new EmployeeDto();
        employeeDto.setName("John Doe");
        employeeDto.setEmail("johnDoe@email.com");
        employeeDto = restTemplate.postForObject("http://localhost:8090/employees", employeeDto, EmployeeDto.class);
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("James Frank");
        customerDto.setEmail("james@gmail.com" +new Random().nextInt(1000));
        ResponseEntity<CustomerDto> responseEntity1 = restTemplate.postForEntity("http://localhost:8090/customers", customerDto, CustomerDto.class);
        customerDto = responseEntity1.getBody();
        List<CarDto> carDtos = restTemplate.exchange("http://localhost:8080/cars/search?by=rental-status&value=available",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<CarDto>>() {
                }).getBody();
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setCarId(carDtos.get(0).getId());
        reservationDto.setReservationDate(LocalDateTime.now());
        reservationDto.setReturnDate(LocalDateTime.now().plusDays(2));
        ResponseEntity<ReservationDto> responseEntity = restTemplate.postForEntity("http://localhost:8090/employees/" + employeeDto.getEmployeeId()
                + "/makeReservation/" + customerDto.getId(), reservationDto, ReservationDto.class);
        reservationDto = responseEntity.getBody();
        System.out.println(responseEntity.getBody());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Return car and reservation");
        System.out.println("------------------------------------------------------------------------");
        PaymentInfoDto paymentInfoDto = new PaymentInfoDto();
        paymentInfoDto.setCreditCardNumber("1234567890123456");
        paymentInfoDto.setSecurityCode("123");
        paymentInfoDto.setExpirationDate("2027-04-26T11:14:51.778");
        paymentInfoDto.setCardHolderName("John Doe");
        reservationDto.setPaymentInfoDto(paymentInfoDto);
        System.out.println(restTemplate.postForEntity("http://localhost:8090/employees/" + employeeDto.getEmployeeId() + "/returnCar", reservationDto, ReservationDto.class).getBody());

    }
}
