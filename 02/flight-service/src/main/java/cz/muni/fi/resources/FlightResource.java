package cz.muni.fi.resources;

import cz.muni.fi.model.Flight;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
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
public class FlightResource {
    /**
     * This is a temporary storage for flights
     */
    private static final Map<Integer, Flight> flights = new HashMap<>();

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
        return RestResponse.ResponseBuilder.ok(flight).build();
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
    public Flight get(@PathParam("id") int id) {
        return flights.get(id);
    }

}

