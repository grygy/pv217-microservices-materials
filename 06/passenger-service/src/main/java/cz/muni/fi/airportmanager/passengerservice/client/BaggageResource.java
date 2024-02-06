package cz.muni.fi.airportmanager.passengerservice.client;

import cz.muni.fi.airportmanager.passengerservice.model.Baggage;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * Client for baggage service
 */
@Path("/baggage/passenger")
@RegisterRestClient(configKey = "baggage-resource")
public interface BaggageResource {

    @GET
    @Path("/{passengerId}")
    Uni<List<Baggage>> getBaggageForPassengerId(@PathParam("passengerId") Long passengerId);
}
