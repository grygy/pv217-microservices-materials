package cz.muni.fi.airportmanager.flightservice.repository;

import cz.muni.fi.airportmanager.flightservice.entity.Flight;
import cz.muni.fi.airportmanager.flightservice.model.FlightStatus;
import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.UniAsserter;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


@QuarkusTest
class FlightRepositoryTest {

    @Inject
    FlightRepository flightRepository;

    Flight createOngoingFlight() {
        var future = Date.from(java.time.Instant.now().plusSeconds(1000*60));
        var past = Date.from(java.time.Instant.now().minusSeconds(1000*60));
        var flight = new Flight();
        flight.setName("Test Flight");
        flight.setAirportFrom("Airport A");
        flight.setAirportTo("Airport B");
        flight.setDepartureTime(past);
        flight.setArrivalTime(future);
        flight.setCapacity(100);
        flight.setStatus(FlightStatus.ACTIVE);

        return flight;
    }

    @Test
    @TestReactiveTransaction
    void testOngoingFlights_Success(UniAsserter asserter) {
        var flight = createOngoingFlight();
        var currentDate = new Date();

        asserter.execute(this.flightRepository::deleteAll)
                .assertEquals(this.flightRepository::count, 0L)
                .execute(() -> this.flightRepository.persist(flight))
                .assertEquals(this.flightRepository::count, 1L)
                .assertThat(
                        () -> this.flightRepository.findOngoingFlights(currentDate),
                        flights -> {
                            assertEquals(1, flights.size());
                            flight.setId(flights.get(0).getId());
                            assertEquals(flight, flights.get(0));
                        }
                );
    }

    @Test
    @TestReactiveTransaction
    void testFutureFlights_Success(UniAsserter asserter) {
        var flight = createOngoingFlight();
        flight.setDepartureTime(Date.from(java.time.Instant.now().plusSeconds(1000*60)));
        flight.setArrivalTime(Date.from(java.time.Instant.now().plusSeconds(1000*60*2)));
        var currentDate = new Date();

        asserter.execute(this.flightRepository::deleteAll)
                .assertEquals(this.flightRepository::count, 0L)
                .execute(() -> this.flightRepository.persist(flight))
                .assertEquals(this.flightRepository::count, 1L)
                .assertThat(
                        () -> this.flightRepository.findFuture(currentDate),
                        flights -> {
                            assertEquals(1, flights.size());
                            flight.setId(flights.get(0).getId());
                            assertEquals(flight, flights.get(0));
                        }
                );
    }

    @Test
    @TestReactiveTransaction
    void testPastFlights_Success(UniAsserter asserter) {
        var flight = createOngoingFlight();
        flight.setDepartureTime(Date.from(java.time.Instant.now().minusSeconds(1000*60*2)));
        flight.setArrivalTime(Date.from(java.time.Instant.now().minusSeconds(1000*60)));
        var currentDate = new Date();

        asserter.execute(this.flightRepository::deleteAll)
                .assertEquals(this.flightRepository::count, 0L)
                .execute(() -> this.flightRepository.persist(flight))
                .assertEquals(this.flightRepository::count, 1L)
                .assertThat(
                        () -> this.flightRepository.findPastFlights(currentDate),
                        flights -> {
                            assertEquals(1, flights.size());
                            flight.setId(flights.get(0).getId());
                            assertEquals(flight, flights.get(0));
                        }
                );
    }

    @Test
    @TestReactiveTransaction
    void testFindByStatus_Success(UniAsserter asserter) {
        var flight = createOngoingFlight();

        asserter.execute(this.flightRepository::deleteAll)
                .assertEquals(this.flightRepository::count, 0L)
                .execute(() -> this.flightRepository.persist(flight))
                .assertEquals(this.flightRepository::count, 1L)
                .assertThat(
                        () -> this.flightRepository.findByStatus(FlightStatus.ACTIVE),
                        flights -> {
                            assertEquals(1, flights.size());
                            flight.setId(flights.get(0).getId());
                            assertEquals(flight, flights.get(0));
                        }
                );
    }

    @Test
    @TestReactiveTransaction
    void testChangeStatus_Success(UniAsserter asserter) {
        var flight = createOngoingFlight();

        asserter.execute(this.flightRepository::deleteAll)
                .assertEquals(this.flightRepository::count, 0L)
                .execute(() -> this.flightRepository.persist(flight))
                .assertEquals(this.flightRepository::count, 1L)
                .execute(() ->
                    this.flightRepository.listAll().onItem().invoke(flights -> {
                        assertEquals(1, flights.size());
                        flight.setId(flights.get(0).getId());
                        assertEquals(flight, flights.get(0));
                    })
                )
                .execute(() -> this.flightRepository.changeStatus(flight.getId(), FlightStatus.CANCELLED))
                .assertThat(
                        () -> this.flightRepository.findById(flight.getId()),
                        found -> {
                            assertEquals(FlightStatus.CANCELLED, found.getStatus());
                        }
                );
    }

//    TODO remove this is just for mocking purposes for use in flight service
//    @Test
//    @RunOnVertxContext // Make sure the test method is run on the Vert.x event loop. aka support async
//    void testFindFutureFlightsFound(UniAsserter asserter) {
//        asserter.assertEquals(() -> flightRepository.count(), 0L);
//
//        asserter.execute(() -> when(flightRepository.count()).thenReturn(Uni.createFrom().item(23L)));
//        asserter.assertEquals(() -> flightRepository.count(), 23L);
//    }
}
