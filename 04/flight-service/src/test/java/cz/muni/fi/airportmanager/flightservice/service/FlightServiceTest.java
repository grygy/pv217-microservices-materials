package cz.muni.fi.airportmanager.flightservice.service;

import cz.muni.fi.airportmanager.flightservice.model.FlightDto;
import cz.muni.fi.airportmanager.flightservice.model.FlightStatus;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class FlightServiceTest {
    private FlightService flightService;

    private FlightDto flight;

    @BeforeEach
    void setUp() {
        flightService = new FlightService();
        flight = new FlightDto();
        flight.id = 1;
        flight.name = "Test Flight";
        flight.airportFrom = "Airport A";
        flight.airportTo = "Airport B";
        flight.departureTime = new Date();
        flight.arrivalTime = new Date();
        flight.capacity = 100;
        flight.status = FlightStatus.ACTIVE;
    }

    @Test
    void testListAllFlights() {
        flightService.createFlight(flight);
        List<FlightDto> flights = flightService.listAll();
        assertNotNull(flights);
        assertFalse(flights.isEmpty());
        assertEquals(1, flights.size());
        assertEquals(flight, flights.get(0));
    }

    @Test
    void testGetFlight_Success() {
        flightService.createFlight(flight);
        FlightDto found = flightService.getFlight(flight.id);
        assertNotNull(found);
        assertEquals(flight, found);
    }

    @Test
    void testGetFlight_NotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            flightService.getFlight(999L);
        });
    }

    @Test
    void testUpdateFlight_Success() {
        flightService.createFlight(flight);
        flight.name = "Updated Test Flight";
        FlightDto updated = flightService.updateFlight(flight);
        assertNotNull(updated);
        assertEquals("Updated Test Flight", updated.name);
    }

    @Test
    void testUpdateFlight_NotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            flightService.updateFlight(flight);
        });
    }

    @Test
    void testDeleteFlight_Success() {
        flightService.createFlight(flight);
        flightService.deleteFlight(flight.id);
        assertThrows(IllegalArgumentException.class, () -> {
            flightService.getFlight(flight.id);
        });
    }

    @Test
    void testDeleteFlight_NotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            flightService.deleteFlight(999L);
        });
    }

    @Test
    void testDeleteAllFlights() {
        flightService.createFlight(flight);
        flightService.deleteAllFlights();
        List<FlightDto> flights = flightService.listAll();
        assertTrue(flights.isEmpty());
    }

    @Test
    void testCreateFlight_Success() {
        FlightDto created = flightService.createFlight(flight);
        assertNotNull(created);
        assertEquals(flight, created);
    }

    @Test
    void testCreateFlight_AlreadyExists() {
        flightService.createFlight(flight);
        assertThrows(IllegalArgumentException.class, () -> {
            flightService.createFlight(flight);
        });
    }

    @Test
    void testCancelFlight_NotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            flightService.cancelFlight(999L);
        });
    }

}
