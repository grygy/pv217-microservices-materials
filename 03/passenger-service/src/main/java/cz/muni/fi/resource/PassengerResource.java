package cz.muni.fi.resource;

import cz.muni.fi.model.Passenger;
import cz.muni.fi.service.PassengerService;
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
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

/**
 * This class is a REST resource that will be hosted on /passenger
 */
@Path("/passenger")
@Tag(name = "Passenger", description = "Passenger CRUD API")
public class PassengerResource {

    @Inject
    PassengerService passengerService;

    /**
     * Get list of all passengers
     *
     * @return list of all passengers
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<List<Passenger>> list() {
        return RestResponse.status(Response.Status.OK, passengerService.listAll());
    }

    /**
     * Create a new passenger
     *
     * @param passenger passenger to create.
     * @return created passenger
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<Passenger> create(Passenger passenger) {
        try {
            var newPassenger = passengerService.createPassenger(passenger);
            return RestResponse.status(Response.Status.CREATED, newPassenger);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.CONFLICT);
        }
    }

    /**
     * Get passenger by id
     *
     * @param id id of passenger
     * @return passenger with given id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<Passenger> get(@PathParam("id") int id) {
        try {
            var passenger = passengerService.getPassenger(id);
            return RestResponse.status(Response.Status.OK, passenger);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.NOT_FOUND);
        }
    }

    /**
     * Update passenger
     *
     * @param id        id of passenger
     * @param passenger passenger to update
     */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<Passenger> update(@PathParam("id") int id, Passenger passenger) {
        if (passenger.id != id) {
            return RestResponse.status(Response.Status.BAD_REQUEST);
        }
        try {
            var updatedPassenger = passengerService.updatePassenger(passenger);
            return RestResponse.status(Response.Status.OK, updatedPassenger);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.NOT_FOUND);
        }
    }

    /**
     * Delete passenger
     *
     * @param id id of passenger
     */
    @DELETE
    @Path("/{id}")
    public RestResponse<Passenger> delete(@PathParam("id") int id) {
        try {
            passengerService.deletePassenger(id);
            return RestResponse.status(Response.Status.OK);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.NOT_FOUND);
        }
    }

    /**
     * Helper method to delete all passengers
     */
    @DELETE
    public RestResponse<Passenger> deleteAll() {
        passengerService.deleteAllPassengers();
        return RestResponse.status(Response.Status.OK);
    }
}
