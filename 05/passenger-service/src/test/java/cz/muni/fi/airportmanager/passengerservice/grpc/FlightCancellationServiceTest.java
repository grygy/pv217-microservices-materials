package cz.muni.fi.airportmanager.passengerservice.grpc;

import cz.muni.fi.airportmanager.proto.FlightCancellationRequest;
import cz.muni.fi.airportmanager.proto.FlightCancellationResponseStatus;
import cz.muni.fi.airportmanager.proto.MutinyFlightCancellationGrpc;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class FlightCancellationServiceTest {

    @GrpcClient
    MutinyFlightCancellationGrpc.MutinyFlightCancellationStub flightCancellationStub;

    @Test
    @RunOnVertxContext
    void testCancelFlight(UniAsserter asserter) {
//        TODO this not working
        asserter.assertThat(() -> flightCancellationStub.cancelFlight(FlightCancellationRequest.newBuilder().setId(1).setReason("Unknown").build()),
                response -> {
                    assertEquals(FlightCancellationResponseStatus.Cancelled, response.getStatus());
                });
//        var response = flightCancellationStub.cancelFlight(FlightCancellationRequest.newBuilder().setId(1).setReason("Unknown").build()).await().indefinitely();
//        assertEquals(FlightCancellationResponseStatus.Cancelled, response.getStatus());
    }
}