package cz.muni.fi.airportmanager.passengerservice.service;

import cz.muni.fi.airportmanager.passengerservice.entity.Notification;
import cz.muni.fi.airportmanager.passengerservice.entity.Passenger;
import cz.muni.fi.airportmanager.passengerservice.model.CreatePassengerDto;
import cz.muni.fi.airportmanager.passengerservice.repository.PassengerRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class PassengerServiceTest {

    @InjectMock
    PassengerRepository passengerRepository;

    @Inject
    PassengerService passengerService;

    @Test
    @RunOnVertxContext
    void shouldGetListOfPassengers(UniAsserter asserter) {
        // TODO implement this test
        // mock the passengerRepository.listAll() method to return a list with one passenger
        // use createTestPassenger helper method
    }

    @Test
    @RunOnVertxContext
    void shouldGetExistingPassenger(UniAsserter asserter) {
        // TODO implement this test
        // mock the passengerRepository.findById() method to return a passenger
    }

    @Test
    @RunOnVertxContext
    void shouldGetPassengersForFlight(UniAsserter asserter) {
        // TODO implement this test
        // mock the passengerRepository.findPassengersForFlight() method to return a list with one passenger
    }

    @Test
    @RunOnVertxContext
    void shouldCreatePassenger(UniAsserter asserter) {
        // TODO implement this test
        // mock the passengerRepository.persist() method to return the passenger
    }

    @Test
    @RunOnVertxContext
    void shouldDeleteExistingPassenger(UniAsserter asserter) {
        // TODO implement this test
        // mock the passengerRepository.deleteById() method to return true
    }

    @Test
    @RunOnVertxContext
    void shouldDeleteAllPassengers(UniAsserter asserter) {
        // TODO implement this test
        // mock the passengerRepository.deleteAll() method to return 1
    }

    @Test
    @RunOnVertxContext
    void shouldAddNotificationByFlightId(UniAsserter asserter) {
        // TODO implement this test
        // mock the passengerRepository.addNotificationByFlightId() method to return void
    }

    @Test
    @RunOnVertxContext
    void shouldFindNotificationsForPassenger(UniAsserter asserter) {
        // TODO implement this test
        // create a passenger and a notification, add the notification to the passenger and test if the notification is found
    }

    @Test
    @RunOnVertxContext
    void shouldHandleNoPassengerFound(UniAsserter asserter) {
        // TODO implement this test
        // mock the passengerRepository.findById() method to return null
    }

    @Test
    @RunOnVertxContext
    void shouldNotDeleteNonExistentPassenger(UniAsserter asserter) {
        // TODO implement this test
        // mock the passengerRepository.deleteById() method to return false
    }

    @Test
    @RunOnVertxContext
    void shouldHandleEmptyListOfPassengers(UniAsserter asserter) {
        // TODO implement this test
        // mock the passengerRepository.listAll() method to return an empty list
    }

    private Passenger createTestPassenger() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        passenger.setFirstName("John");
        passenger.setLastName("Doe");
        passenger.setFlightId(1L);
        passenger.setEmail("john@gmail.com");
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

    private Notification createNotification() {
        Notification notification = new Notification();
        notification.message = "Test message";
        return notification;
    }

}
