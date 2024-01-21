package cz.muni.fi.grpc;

import cz.muni.fi.model.Notification;
import cz.muni.fi.proto.FlightCancellation;
import cz.muni.fi.proto.FlightCancellationRequest;
import cz.muni.fi.proto.FlightCancellationResponse;
import cz.muni.fi.service.NotificationService;
import cz.muni.fi.service.PassengerService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;


public class FlightCancellationService implements FlightCancellation {

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
        return Uni.createFrom().item(FlightCancellationResponse.newBuilder().setStatus("Successfully notified").build());
    }
}
