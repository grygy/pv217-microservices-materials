package cz.muni.fi.grpc;

import cz.muni.fi.proto.FlightCancellation;
import cz.muni.fi.proto.FlightCancellationRequest;
import cz.muni.fi.proto.FlightCancellationResponse;
import cz.muni.fi.service.FlightService;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;

@GrpcService
public class FlightCancellationService implements FlightCancellation {

    @Inject
    FlightService flightService;

//    TODO create the second service that will consume this one.

    @Override
    public Uni<FlightCancellationResponse> cancelFlight(FlightCancellationRequest request) {
        try {
            flightService.cancelFlight(request.getId());
            return Uni.createFrom().item(FlightCancellationResponse.newBuilder().setStatus("Successfully cancelled").build());
        } catch (IllegalArgumentException e) {
            return Uni.createFrom().item(FlightCancellationResponse.newBuilder().setStatus("Flight cannot be cancelled. " + e.getMessage()).build());
        }
    }
}
