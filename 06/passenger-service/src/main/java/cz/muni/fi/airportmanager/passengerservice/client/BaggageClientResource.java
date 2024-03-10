package cz.muni.fi.airportmanager.passengerservice.client;

import cz.muni.fi.airportmanager.passengerservice.model.Baggage;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

/**
 * Client for baggage service
 */
// TODO add @Path annotation that will specify the path to the baggage resource
// TODO add @RegisterRestClient annotation. Don't forget to specify the configKey
@Path("/baggage/passenger")
@RegisterRestClient(configKey = "baggage-resource")
public interface BaggageClientResource {

    // TODO add getBaggageForPassengerId method that will get baggage for passenger with given id
    // It should take one @PathParam argument passengerId
    // The method should be asynchronous and return Uni
    // TODO add REST method annotation that will correspond to HTTP method of the baggage resource
    // TODO add @Path annotation that will specify the path for getting baggage for passenger
    @GET
    @Path("/{passengerId}")
    Uni<List<Baggage>> getBaggageForPassengerId(@PathParam("passengerId") Long passengerId);
}
