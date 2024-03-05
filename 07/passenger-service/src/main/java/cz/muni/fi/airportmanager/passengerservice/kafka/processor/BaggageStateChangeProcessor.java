package cz.muni.fi.airportmanager.passengerservice.kafka.processor;

import cz.muni.fi.airportmanager.passengerservice.entity.Notification;
import cz.muni.fi.airportmanager.passengerservice.kafka.model.BaggageStateChange;
import cz.muni.fi.airportmanager.passengerservice.service.PassengerService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;


@ApplicationScoped
public class BaggageStateChangeProcessor {

    // TODO use passengerService to add notification for passenger

    @Inject
    PassengerService passengerService;

    /**
     * Process baggage state change
     *
     * @param baggageStateChange baggage state change
     */
//    TODO process baggage state change from Kafka using Incoming annotation
    @Incoming("baggage-state-change")
    public Uni<Void> process(BaggageStateChange baggageStateChange) {
        // TODO create a notification for passenger and add it using passengerService with the following text:
        //  "Baggage state changed to " + baggageStateChange.newStatus + " for baggage " + baggageStateChange.baggageId;
        var notification = new Notification();
        notification.passengerId = baggageStateChange.passengerId;
        notification.message = "Baggage state changed to " + baggageStateChange.newStatus + " for baggage " + baggageStateChange.baggageId;
        return passengerService.addNotificationForPassenger(baggageStateChange.passengerId, notification);
    }

}
