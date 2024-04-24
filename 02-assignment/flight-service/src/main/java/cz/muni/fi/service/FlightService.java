package cz.muni.fi.service;

import cz.muni.fi.model.Flight;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped // This beam will be created once per application and live as long as the application lives
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
        // TODO implement this method
    }

    /**
     * Get flight by id
     *
     * @param id flight id
     * @return flight with given id
     * @throws IllegalArgumentException if flight with given id does not exist
     */
    public Flight getFlight(int id) {
        // TODO implement this method
    }

    /**
     * Create a new flight
     *
     * @param flight flight to create.
     * @return created flight
     * @throws IllegalArgumentException if flight with given id already exists
     */
    public Flight createFlight(Flight flight) {
        // TODO implement this method
    }

    /**
     * Update flight
     *
     * @param flight flight to update
     * @return updated flight
     * @throws IllegalArgumentException if flight with given id does not exist
     */
    public Flight updateFlight(Flight flight) {
        // TODO implement this method
    }

    /**
     * Delete flight
     *
     * @param id flight id
     * @throws IllegalArgumentException if flight with given id does not exist
     */
    public void deleteFlight(int id) {
        // TODO implement this method
    }

    /**
     * Delete all flights
     */
    public void deleteAllFlights() {
        // TODO implement this method
    }
}
