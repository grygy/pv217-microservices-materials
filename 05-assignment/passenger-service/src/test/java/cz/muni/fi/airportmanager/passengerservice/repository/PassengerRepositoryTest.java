package cz.muni.fi.airportmanager.passengerservice.repository;

import cz.muni.fi.airportmanager.passengerservice.entity.Notification;
import cz.muni.fi.airportmanager.passengerservice.entity.Passenger;
import cz.muni.fi.airportmanager.passengerservice.model.NotificationDto;
import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.UniAsserter;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class PassengerRepositoryTest {

    @Inject
    PassengerRepository passengerRepository;

    private Passenger createTestPassenger() {
        Passenger passenger = new Passenger();
        passenger.setFirstName("John");
        passenger.setLastName("Doe");
        passenger.setEmail("johndoe@example.com");
        passenger.setFlightId(1L);
        return passenger;
    }

    private Notification createTestNotification() {
        Notification notification = new Notification();
        notification.message = "Test notification message";
        return notification;
    }

    @Test
    @TestReactiveTransaction
    void shouldFindNotificationsForPassenger(UniAsserter asserter) {
        // TODO implement this test
        // create a passenger and a notification, add the notification to the passenger and test if the notification is found
    }

    @Test
    @TestReactiveTransaction
    void shouldAddNotificationByFlightId(UniAsserter asserter) {
        // TODO implement this test
        // It should test that the notification is added to the appropriate passengers
        // call addNotificationByFlightId and then check if the notification is present in the passenger's notifications
        // using findNotificationsForPassenger method
    }

    @Test
    @TestReactiveTransaction
    void shouldFindPassengersForFlight(UniAsserter asserter) {
        // TODO implement this test
        // Persist a passenger and then get the its record by flight id
        // test findPassengersForFlight method
    }

    @Test
    @TestReactiveTransaction
    void shouldFindNotificationsWithEmail(UniAsserter asserter) {
        // TODO implement this test
        // this test should find all notifications with the email of the passenger
        // test findNotificationsWithEmail method
    }

    @Test
    @TestReactiveTransaction
    void shouldHandleNoNotificationsForPassenger(UniAsserter asserter) {
        // TODO implement this test
        // test findNotificationsForPassenger method
    }

    @Test
    @TestReactiveTransaction
    void shouldHandleInvalidPassengerIdForNotifications(UniAsserter asserter) {
        // TODO implement this test
        // test findNotificationsForPassenger method
    }

    @Test
    @TestReactiveTransaction
    void shouldNotAddNotificationToNonExistentFlight(UniAsserter asserter) {
        // TODO implement this test
        // test addNotificationByFlightId method
    }

    @Test
    @TestReactiveTransaction
    void shouldHandleNoPassengersForFlight(UniAsserter asserter) {
        // TODO implement this test
        // test findPassengersForFlight method
    }

    @Test
    @TestReactiveTransaction
    void shouldHandleEmptyPassengerRepositoryForNotificationsWithEmail(UniAsserter asserter) {
        // TODO implement this test
        // test findNotificationsWithEmail method
    }


    @Test
    @TestReactiveTransaction
    void shouldNotFindNonExistentPassenger(UniAsserter asserter) {
        // TODO implement this test
        // test findById method
    }

    @Test
    @TestReactiveTransaction
    void shouldDeletePassenger(UniAsserter asserter) {
        // TODO implement this test
        // test deleteById method
    }
}
