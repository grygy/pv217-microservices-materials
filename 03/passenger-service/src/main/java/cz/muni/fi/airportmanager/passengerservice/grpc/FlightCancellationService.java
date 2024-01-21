package cz.muni.fi.airportmanager.passengerservice.grpc;

import cz.muni.fi.airportmanager.passengerservice.model.Notification;
import cz.muni.fi.airportmanager.passengerservice.service.NotificationService;
import cz.muni.fi.airportmanager.passengerservice.service.PassengerService;
import cz.muni.fi.airportmanager.proto.FlightCancellation;
import cz.muni.fi.airportmanager.proto.FlightCancellationRequest;
import cz.muni.fi.airportmanager.proto.FlightCancellationResponse;
import cz.muni.fi.airportmanager.proto.MutinyFlightCancellationGrpc;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;


@GrpcService
public class FlightCancellationService extends MutinyFlightCancellationGrpc.FlightCancellationImplBase {

    @Inject
    NotificationService notificationService;

    @Inject
    PassengerService passengerService;

    @Override
    public Uni<FlightCancellationResponse> cancelFlight(FlightCancellationRequest request) {
        var flightId = request.getId();
        var passengersOnFlight = passengerService.getPassengersForFlight(flightId);
        for (var passenger : passengersOnFlight) {
            var notification = new Notification();
            notification.email = passenger.email;
            notification.message = "Your flight " + flightId + " has been cancelled.";
            notification.passengerId = passenger.id;
            notificationService.createNotification(notification);
        }
        return Uni.createFrom().item(FlightCancellationResponse.newBuilder().setStatus(FlightCancellationResponse.ResponseStatus.Cancelled).build());
    }
}
