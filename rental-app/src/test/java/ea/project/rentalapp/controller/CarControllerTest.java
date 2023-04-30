package ea.project.rentalapp.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class CarControllerTest {

    @BeforeEach
    void setUp() {
        RestAssured.port = 8090;
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "";
    }


    @Test
    public void testFindAll() {
        given()
                .when()
                .get("/cars")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testFindAvailableCars() {
        given()
                .when()
                .get("/cars/available")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", notNullValue());
    }



    @Test
    public void testFindRentedCars() {
        given()
                .when()
                .get("/cars/rented")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", notNullValue());
    }

    @Test
    public void testFindByBrand() {
        given()
                .pathParam("brand", "TOYOTA")
                .when()
                .get("/cars/brand/{brand}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", notNullValue());
    }

    @Test
    public void testFindById() {
        given()
                .pathParam("id", "64474a37ab319e63d011592a")
                .when()
                .get("/cars/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo("64474a37ab319e63d011592a"))
                .body("brand", equalTo("LEXUS"))
                .body("model", equalTo("IS200"))
                .body("licensePlate", equalTo("AAS18S"));
    }

    @Test
    public void testFindByLicensePlate() {
        given()
                .pathParam("licensePlate", "AAS18S")
                .when()
                .get("/cars/licensePlate/{licensePlate}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo("64474a37ab319e63d011592a"))
                .body("brand", equalTo("LEXUS"))
                .body("model", equalTo("IS200"))
                .body("licensePlate", equalTo("AAS18S"));
    }

}
