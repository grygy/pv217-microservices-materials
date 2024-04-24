package cz.muni.fi.airportmanager.passengerservice.resource;

import cz.muni.fi.airportmanager.passengerservice.entity.Notification;
import cz.muni.fi.airportmanager.passengerservice.entity.Passenger;
import cz.muni.fi.airportmanager.passengerservice.model.CreatePassengerDto;
import cz.muni.fi.airportmanager.passengerservice.service.PassengerService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestHTTPEndpoint(PassengerResource.class)
class PassengerResourceTest {

    @InjectMock
    PassengerService passengerService;

    @Test
    void shouldGetEmptyListOfPassengers() {
        // TODO implement this test
        // mock the passengerService.listAll() method to return an empty list
    }

    @Test
    void shouldGetListOfPassengers() {
        // TODO implement this test
        // mock the passengerService.listAll() method to return a list with one passenger

    }

    @Test
    void shouldCreatePassenger() {
        // TODO implement this test
        // mock the passengerService.createPassenger() method to return a passenger with ID 1
    }

    @Test
    void shouldGetExistingPassenger() {
        // TODO implement this test
        // mock the passengerService.getPassenger() method to return a passenger with ID 1
    }

    @Test
    void shouldNotGetNonexistingPassenger() {
        // TODO implement this test
        // mock the passengerService.getPassenger() method to return null
    }

    @Test
    void shouldDeleteExistingPassenger() {
        // TODO implement this test
        // mock the passengerService.deletePassenger() method to return true
    }

    @Test
    void shouldNotDeleteNonexistingPassenger() {
        // TODO implement this test
        // mock the passengerService.deletePassenger() method to return false
    }


    @Test
    void shouldDeleteAllPassengers() {
        // TODO implement this test
        // mock the passengerService.deleteAllPassengers() method to return 0
    }

    @Test
    void shouldNotFindPassengersForNonexistentFlight() {
        // TODO implement this test
        // mock the passengerService.getPassengersForFlight() method to return an empty list
    }


    @Test
    void shouldGetPassengersForFlight() {
        // TODO implement this test
        // mock the passengerService.getPassengersForFlight() method to return a list with one passenger
    }

    @Test
    void shouldGetEmptyListOfPassengersForFlightWhenNoPassengers() {
        // TODO implement this test
        // mock the passengerService.getPassengersForFlight() method to return an empty list
    }

    @Test
    void shouldGetNotificationsForPassenger() {
        // TODO implement this test
        // mock the passengerService.findNotificationsForPassenger() method to return a list with one notification
    }

    @Test
    void shouldGetEmptyListOfNotificationsForPassengerWhenNoNotifications() {
        // TODO implement this test
        // mock the passengerService.findNotificationsForPassenger() method to return an empty list
    }

    @Test
    void shouldNotFindNotificationsForNonexistentPassenger() {
        // TODO implement this test
        // mock the passengerService.findNotificationsForPassenger() method to return an empty list
    }

    // Helper methods
    private Notification createNotification() {
        Notification notification = new Notification();
        notification.message = "Test notification";
        return notification;
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

