package cz.muni.fi.airportmanager.passengerservice.resource;

import cz.muni.fi.airportmanager.passengerservice.model.CreatePassengerDto;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

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

    @AfterEach
        // this method will be called after each test
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
    void shouldGetEmptyListOfPassengers() {
        // TODO implement this test
    }


    @Test
    void shouldDeleteAllPassengers() {
        // TODO implement this test
        // create some passengers and then delete them all
    }


    @Test
    void shouldGetPassengersForFlight() {
        // TODO implement this test
        // create a passenger and then get the list of passengers for the flight
    }

    @Test
    void shouldGetEmptyListOfPassengersForFlightWhenNoPassengers() {
        // TODO implement this test
        // get the list of passengers for a flight that has no passengers
    }

    @Test
    void shouldGetEmptyNotificationsForPassenger() {
        // TODO implement this test
        // get the list of notifications for a passenger that has no notifications
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

