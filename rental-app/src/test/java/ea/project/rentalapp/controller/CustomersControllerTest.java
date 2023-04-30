package ea.project.rentalapp.controller;

import ea.project.rentalapp.service.dto.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomersControllerTest {
    CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8090;
        RestAssured.basePath = "";

    }

    @Test
    public void getCustomersTest() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/customers")
                .then()
                .statusCode(200)
                .body("$.size()", greaterThan(0));
    }

    @Test
    public void getCustomerByIdTest() {
        customerDto = new CustomerDto();
        customerDto.setAddressDto(new AddressDto());
        customerDto.setCustomerNumber("C0001");
        customerDto.setName("John");
        customerDto.setEmail("email@email.com" + new Random().nextInt(100));
        given()
                .contentType("application/json")
                .body(customerDto)
                .when()
                .post()
                .then()
                .body("id", not(0));
    }


    @Test
    public void updateCustomerTest() {
        CustomerDto dto = new CustomerDto();
        dto.setEmail("email@email.com" + new Random().nextInt(200));
        dto.setName("John");
        dto.setCustomerNumber("C0001");
        dto.setAddressDto(new AddressDto(0, "Main", "Fairfield", "IA", "52557"));
        int customerId = given()
                .when()
                .contentType(ContentType.JSON)
                .body(dto).post("/customers")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .path("id");

        dto.setName("Jack");
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(dto).put("/customers/{id}", customerId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", equalTo("Jack"));
    }

    @Test
    public void saveCustomerTest() {
        CustomerDto dto = new CustomerDto();
        dto.setEmail("email@email.com" + new Random().nextInt(200));
        dto.setName("John");
        dto.setCustomerNumber("C0001");
        dto.setAddressDto(new AddressDto(0, "Main", "Fairfield", "IA", "52557"));
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(dto).post("/customers")
                .then()
                .statusCode(200);


    }

    @Test
    public void deleteCustomerTest() {
        CustomerDto dto = new CustomerDto();
        dto.setEmail("email@email.com" + new Random().nextInt(200));
        dto.setName("John");
        dto.setCustomerNumber("C0001");
        dto.setAddressDto(new AddressDto(0, "Main", "Fairfield", "IA", "52557"));
        int customerId = given()
                .when()
                .contentType(ContentType.JSON)
                .body(dto).post("/customers")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .path("id");
        given()
                .when()
                .delete("/customers/{id}", customerId)
                .then()
                .statusCode(200);
    }

    @Test
    public void reserveCarTest() throws InterruptedException {
        CustomerDto dto = new CustomerDto();
        dto.setEmail("email@email.com" + new Random().nextInt(200000));
        dto.setName("John");
        dto.setCustomerNumber("C0001");
        dto.setAddressDto(new AddressDto(0, "Main", "Fairfield", "IA", "52557"));
        int customerId = given()
                .when()
                .contentType(ContentType.JSON)
                .body(dto).post("/customers")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .path("id");
        Thread.sleep(1000);
        CarDto carDto = new CarDto();
        carDto.setCarType("SUV");
        carDto.setBrand("TOYOTA");
        carDto.setLicensePlate("PLATE" + new Random().nextInt(100000));
        carDto.setPricePerDay(BigDecimal.valueOf(100.00));
        carDto.setModel("RAV4");
        carDto.setRentalStatus("AVAILABLE");
        String carId = given().contentType(ContentType.JSON)
                .body(carDto)
                .post("/cars")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .path("id");
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setCarId(carId);
        reservationDto.setReservationDate(LocalDateTime.now());
        reservationDto.setReturnDate(LocalDateTime.now().plusDays(5));

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(reservationDto)
                .post("/customers/{customerId}/reserve", customerId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("reservationStatus", equalTo("RESERVED"));

        //remove the car
        given()
                .when()
                .delete("/cars/{carId}", carId)
                .then()
                .statusCode(200);
        //remove the customer
        given()
                .when()
                .delete("/customers/{customerId}", customerId)
                .then()
                .statusCode(200);

    }

    @Test
    @Ignore
    public void rentCarTest() {
        CustomerDto dto = new CustomerDto();
        dto.setEmail("email@email.com" + new Random().nextInt(200000));
        dto.setName("John");
        dto.setCustomerNumber("C0001");
        dto.setAddressDto(new AddressDto(0, "Main", "Fairfield", "IA", "52557"));
        int customerId = given()
                .when()
                .contentType(ContentType.JSON)
                .body(dto).post("/customers")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .path("id");
        CarDto carDto = new CarDto();
        carDto.setCarType("SUV");
        carDto.setBrand("TOYOTA");
        carDto.setLicensePlate("PLATE" + new Random().nextInt(100000));
        carDto.setPricePerDay(BigDecimal.valueOf(100.00));
        carDto.setModel("RAV4");
        carDto.setRentalStatus("AVAILABLE");
        String carId = given().contentType(ContentType.JSON)
                .body(carDto)
                .post("/cars")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .path("id");
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setCarId(carId);
        reservationDto.setReservationDate(LocalDateTime.now());
        reservationDto.setReturnDate(LocalDateTime.now().plusDays(5));

        int reservationId = given()
                .contentType(ContentType.JSON)
                .when()
                .body(reservationDto)
                .post("/customers/{customerId}/reserve", customerId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().path("reservationId");


        given()
                .contentType(ContentType.JSON)
                .when()
                .post("customers/{customerId}/rent/{reservationId}", customerId, reservationId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("reservationStatus", equalTo("RENTED"));
        //remove the car
        given()
                .when()
                .delete("/cars/{carId}", carId)
                .then()
                .statusCode(200);
        //remove the customer
        given()
                .when()
                .delete("/customers/{customerId}", customerId)
                .then()
                .statusCode(200);

    }

    @Test
    void returnCar() {
        CustomerDto dto = new CustomerDto();
        dto.setEmail("email@email.com" + new Random().nextInt(200000));
        dto.setName("John");
        dto.setCustomerNumber("C0001");
        dto.setAddressDto(new AddressDto(0, "Main", "Fairfield", "IA", "52557"));
        int customerId = given()
                .when()
                .contentType(ContentType.JSON)
                .body(dto).post("/customers")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .path("id");
        CarDto carDto = new CarDto();
        carDto.setCarType("SUV");
        carDto.setBrand("TOYOTA");
        carDto.setLicensePlate("PLATE" + new Random().nextInt(100000));
        carDto.setPricePerDay(BigDecimal.valueOf(100.00));
        carDto.setModel("RAV4");
        carDto.setRentalStatus("AVAILABLE");
        String carId = given().contentType(ContentType.JSON)
                .body(carDto)
                .post("/cars")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .path("id");
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setCarId(carId);
        reservationDto.setReservationDate(LocalDateTime.now());
        reservationDto.setReturnDate(LocalDateTime.now().plusDays(5));

        int reservationId = given()
                .contentType(ContentType.JSON)
                .when()
                .body(reservationDto)
                .post("/customers/{customerId}/reserve", customerId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().path("reservationId");


        given()
                .contentType(ContentType.JSON)
                .when()
                .post("customers/{customerId}/rent/{reservationId}", customerId, reservationId)
                .then()
                .statusCode(200);

        PaymentInfoDto paymentInfoDto = new PaymentInfoDto();
        paymentInfoDto.setPaymentDate(LocalDateTime.now());
        paymentInfoDto.setCreditCardNumber("1234567890123456");
        paymentInfoDto.setAmount(BigDecimal.valueOf(500.00));
        paymentInfoDto.setCardHolderName("PAID");
        paymentInfoDto.setExpirationDate("12/2022");
        paymentInfoDto.setSecurityCode("123");
        reservationDto.setPaymentInfoDto(paymentInfoDto);
        reservationDto.setReservationId(reservationId);
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(reservationDto)
                .post("customers/{customerId}/return", customerId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("reservationStatus", equalTo("RETURNED"));


        //remove the car
        given()
                .when()
                .delete("/cars/{carId}", carId)
                .then()
                .statusCode(200);
        //remove the customer
        given()
                .when()
                .delete("/customers/{customerId}", customerId)
                .then()
                .statusCode(200);

    }


}