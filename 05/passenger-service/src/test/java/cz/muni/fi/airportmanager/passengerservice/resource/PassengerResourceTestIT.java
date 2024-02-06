package cz.muni.fi.airportmanager.passengerservice.resource;

import cz.muni.fi.airportmanager.passengerservice.entity.Passenger;
import cz.muni.fi.airportmanager.passengerservice.model.CreatePassengerDto;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusIntegrationTest
@TestHTTPEndpoint(PassengerResource.class)
class PassengerResourceTestIT {

    String savePassenger(CreatePassengerDto passengerDto) {
        return given().contentType("application/json")
                .body(passengerDto)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .response()
                .path("id")
                .toString();
    }

    @AfterEach // this method will be called after each test
    void tearDown() {
        given().when()
                .delete()
                .then()
                .statusCode(200);
    }

    @Test
    void shouldSaveAndGetListOfPassengers() {
        // Take this as an example for the rest of the tests
        CreatePassengerDto testPassenger = createTestPassengerDto();
        var id = savePassenger(testPassenger);

        given().when()
                .get("/" + id)
                .then()
                .statusCode(200)
                .body("email", is(testPassenger.email));
    }

    @Test
    void shouldSaveAndGetEmptyListOfPassengers() {
        // TODO implement this test
        given().when()
                .get()
                .then()
                .statusCode(200)
                .body(is("[]"));
    }

    @Test
    void shouldGetAndGetPassenger() {
        // TODO implement this test
        CreatePassengerDto testPassenger = createTestPassengerDto();
        savePassenger(testPassenger);

        given().when()
                .get()
                .then()
                .statusCode(200)
                .body("size()", is(1));
    }



    @Test
    void shouldDeleteAllPassengers() {
        // TODO implement this test
        given().when()
                .delete()
                .then()
                .statusCode(200);
    }


    @Test
    void shouldGetPassengersForFlight() {
        // TODO implement this test
        CreatePassengerDto testPassenger = createTestPassengerDto();
        var id = savePassenger(testPassenger);

        given().when()
                .get("/flight/" + id)
                .then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void shouldGetEmptyListOfPassengersForFlightWhenNoPassengers() {
        // TODO implement this test
        long id = 99L;

        given().when()
                .get("/flight/" + id)
                .then()
                .statusCode(200)
                .body(is("[]"));
    }

    @Test
    void shouldGetEmptyNotificationsForPassenger() {
        // TODO implement this test
        CreatePassengerDto testPassenger = createTestPassengerDto();
        var id = savePassenger(testPassenger);

        given().when()
                .get("/" + id + "/notifications")
                .then()
                .statusCode(200)
                .body("size()", is(0));
    }

    private Passenger createPassenger() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        passenger.setFirstName("John");
        passenger.setLastName("Doe");
        passenger.setNotifications(Collections.emptyList());
        passenger.setEmail("johndoe@gmail.com");
        passenger.setFlightId(1L);
        return passenger;
    }

    private CreatePassengerDto createTestPassengerDto() {
        CreatePassengerDto passengerDto = new CreatePassengerDto();
        passengerDto.firstName = "John";
        passengerDto.lastName = "Doe";
        passengerDto.flightId = 1L;
        passengerDto.email = "john@gmail.com";
        return passengerDto;
    }
}
