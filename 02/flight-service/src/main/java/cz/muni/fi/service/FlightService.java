package cz.muni.fi.service;

import cz.muni.fi.model.Flight;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class FlightService {
    /**
     * This is a temporary storage for flights
     */
    private final Map<Integer, Flight> flights = new HashMap<>();

    /**
     * Get list of all flights
     *
     * @return list of all flights
     */
    public List<Flight> listAll() {
        return flights.values().stream().toList();
    }

    /**
     * Get flight by id
     *
     * @param id flight id
     * @return flight with given id
     */
    public Flight getFlight(int id) {
        if (flights.get(id) == null) {
            throw new IllegalArgumentException("Flight with id " + id + " does not exist");
        }
        return flights.get(id);
    }

    /**
     * Create a new flight
     *
     * @param flight flight to create.
     * @return created flight
     */
    public Flight createFlight(Flight flight) {
        if (flights.get(flight.id) != null) {
            throw new IllegalArgumentException("Flight with id " + flight.id + " already exists");
        }
        flights.put(flight.id, flight);
        return flight;
    }

    /**
     * Update flight
     *
     * @param flight flight to update
     * @return updated flight
     */
    public Flight updateFlight(Flight flight) {
        if (flights.get(flight.id) == null) {
            throw new IllegalArgumentException("Flight with id " + flight.id + " does not exist");
        }
        flights.put(flight.id, flight);
        return flight;
    }

    /**
     * Delete flight
     *
     * @param id flight id
     */
    public void deleteFlight(int id) {
        if (flights.get(id) == null) {
            throw new IllegalArgumentException("Flight with id " + id + " does not exist");
        }
        flights.remove(id);
    }

    /**
     * Delete all flights
     */
    public void deleteAllFlights() {
        flights.clear();
    }
}
