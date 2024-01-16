package cz.muni.fi.resources;


import cz.muni.fi.model.Flight;
import cz.muni.fi.model.FlightStatus;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@QuarkusTest
class FlightResourceTest {

    private FlightResource flightResource;
    private Flight testFlight;

    @BeforeEach
    void setUp() {
        flightResource = new FlightResource();
        testFlight = new Flight();
        testFlight.id = 1;
        testFlight.name = "Test Flight";
        testFlight.airportFrom = "Airport A";
        testFlight.airportTo = "Airport B";
        testFlight.departureTime = new Date();
        testFlight.arrivalTime = new Date();
        testFlight.capacity = 100;
        testFlight.status = FlightStatus.ACTIVE; // Assuming ACTIVE is a valid enum
        FlightResource.flights.clear();
    }

    @Test
    void testListEmpty() {
        List<Flight> flights = flightResource.list();
        assertTrue(flights.isEmpty());
    }

    @Test
    void testListWithFlights() {
        flightResource.create(testFlight);
        List<Flight> flights = flightResource.list();
        assertFalse(flights.isEmpty());
        assertEquals(1, flights.size());
    }

    @Test
    void testCreateFlight() {
        RestResponse<Flight> response = flightResource.create(testFlight);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    void testCreateDuplicateFlight() {
        flightResource.create(testFlight);
        RestResponse<Flight> response = flightResource.create(testFlight);
        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
    }

    @Test
    void testGetExistingFlight() {
        flightResource.create(testFlight);
        Flight retrievedFlight = flightResource.get(testFlight.id);
        assertNotNull(retrievedFlight);
        assertEquals(testFlight.id, retrievedFlight.id);
    }

    @Test
    void testGetNonExistingFlight() {
        Flight retrievedFlight = flightResource.get(99); // Assuming ID 99 does not exist
        assertNull(retrievedFlight);
    }

    @Test
    void testUpdateExistingFlight() {
        flightResource.create(testFlight);
        testFlight.name = "Updated Name";
        RestResponse<Flight> response = flightResource.update(testFlight.id, testFlight);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Updated Name",

                flightResource.get(testFlight.id).name);
    }


    @Test
    void testUpdateNonExistingFlight() {
        RestResponse<Flight> response = flightResource.update(1, testFlight);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void testUpdateFlightWithMismatchedId() {
        flightResource.create(testFlight);
        RestResponse<Flight> response = flightResource.update(99, testFlight); // Mismatched ID
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testDeleteExistingFlight() {
        flightResource.create(testFlight);
        RestResponse<Flight> response = flightResource.delete(testFlight.id);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNull(flightResource.get(testFlight.id));
    }

    @Test
    void testDeleteNonExistingFlight() {
        RestResponse<Flight> response = flightResource.delete(99); // Assuming ID 99 does not exist
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

}