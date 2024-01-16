package cz.muni.fi.resources;

import cz.muni.fi.model.Flight;
import jakarta.enterprise.context.ApplicationScoped;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a REST resource that will be hosted on /flight
 */
@Path("/flight")
@ApplicationScoped
public class FlightResource {
    /**
     * This is a temporary storage for flights
     */
    static final Map<Integer, Flight> flights = new HashMap<>();

    /**
     * Get list of all flights
     *
     * @return list of all flights
     */
    @GET // This method process GET requests on /flight
    @Produces(MediaType.APPLICATION_JSON) // This will set Content-Type header to application/json
    public List<Flight> list() {
        return flights.values().stream().toList();
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
        if (flights.get(flight.id) != null) {
            return RestResponse.status(Response.Status.CONFLICT);
        }
        flights.put(flight.id, flight);
        return RestResponse.status(Response.Status.CREATED, flight);
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
        if (flights.get(id) == null) {
            return RestResponse.status(Response.Status.NOT_FOUND);
        }
        return RestResponse.status(Response.Status.OK, flights.get(id));
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
        if (flight.id != id) {
            return RestResponse.status(Response.Status.BAD_REQUEST);
        }
        if (flights.get(id) == null) {
            return RestResponse.status(Response.Status.NOT_FOUND);
        }
        flights.put(id, flight);
        return RestResponse.status(Response.Status.OK, flight);
    }

    /**
     * Delete flight
     *
     * @param id id of flight
     */
    @DELETE
    @Path("/{id}")
    public RestResponse<Flight> delete(@PathParam("id") int id) {
        if (flights.get(id) == null) {
            return RestResponse.status(Response.Status.NOT_FOUND);
        }
        flights.remove(id);
        return RestResponse.status(Response.Status.OK);
    }

}

