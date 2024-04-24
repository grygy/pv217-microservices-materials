package cz.muni.fi.resources;

import cz.muni.fi.model.Flight;
import cz.muni.fi.service.FlightService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

/**
 * This class is a REST resource that will be hosted on /flight
 */
@Path("/flight")
public class FlightResource {

    @Inject
    FlightService flightService;


    /**
     * Get list of all flights
     *
     * @return list of all flights
     */
    @GET // This method process GET requests on /flight
    @Produces(MediaType.APPLICATION_JSON) // This will set Content-Type header to application/json
    public List<Flight> list() {
        // TODO implement this method
    }

    /**
     * Create a new flight
     *
     * @param flight flight to create.
     * @return created flight
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON) // This will indicate that this method consumes JSON
    public RestResponse<Flight> create(Flight flight) { // Converts JSON from request body to Flight object
        // TODO implement this method
    }


    /**
     * Get flight by id
     *
     * @param id id of flight
     * @return flight with given id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<Flight> get(@PathParam("id") int id) {
        // TODO implement this method
    }


    /**
     * Update flight
     *
     * @param id     id of flight
     * @param flight flight to update
     */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<Flight> update(@PathParam("id") int id, Flight flight) {
        // TODO implement this method
    }

    /**
     * Delete flight
     *
     * @param id id of flight
     */
    @DELETE
    @Path("/{id}")
    public RestResponse<Flight> delete(@PathParam("id") int id) {
        try {
            flightService.deleteFlight(id);
            return RestResponse.status(Response.Status.OK);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.NOT_FOUND);
        }
    }

    /**
     * Helper method for to delete all flights
     */
    @DELETE
    public RestResponse<Flight> deleteAll() {
        flightService.deleteAllFlights();
        return RestResponse.status(Response.Status.OK);
    }

}

