package cz.muni.fi.airportmanager.flightservice.service;

import cz.muni.fi.airportmanager.flightservice.model.FlightDto;
import cz.muni.fi.airportmanager.flightservice.model.FlightStatus;
import cz.muni.fi.airportmanager.proto.FlightCancellationRequest;
import cz.muni.fi.airportmanager.proto.FlightCancellationResponseStatus;
import cz.muni.fi.airportmanager.proto.MutinyFlightCancellationGrpc;
import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class FlightService {
    /**
     * This is a temporary storage for flights
     */
    private final Map<Long, FlightDto> flights = new HashMap<>();

    @GrpcClient("passenger-service")
    MutinyFlightCancellationGrpc.MutinyFlightCancellationStub flightCancellationStub;

    /**
     * Get list of all flights
     *
     * @return list of all flights
     */
    public Uni<List<FlightDto>> listAll() {
        return Uni.createFrom().item(() -> flights.values().stream().toList());
    }

    /**
     * Get flight by id
     *
     * @param id flight id
     * @return flight with given id
     * @throws IllegalArgumentException if flight with given id does not exist
     */
    public Uni<FlightDto> getFlight(Long id) {
        return Uni.createFrom().item(Unchecked.supplier(() -> {
            if (flights.get(id) == null) {
                throw new IllegalArgumentException("Flight with id " + id + " does not exist");
            }
            return flights.get(id);
        }));
    }

    /**
     * Create a new flight
     *
     * @param flight flight to create.
     * @return created flight
     * @throws IllegalArgumentException if flight with given id already exists
     */
    public Uni<FlightDto> createFlight(FlightDto flight) {
        return Uni.createFrom().item(Unchecked.supplier(() -> {
            if (flights.get(flight.id) != null) {
                throw new IllegalArgumentException("Flight with id " + flight.id + " already exists");
            }
            flights.put(flight.id, flight);
            return flight;
        }));
    }


    /**
     * Update flight
     *
     * @param flight flight to update
     * @return updated flight
     * @throws IllegalArgumentException if flight with given id does not exist
     */
    public Uni<FlightDto> updateFlight(FlightDto flight) {
        return Uni.createFrom().item(Unchecked.supplier(() -> {
            if (flights.get(flight.id) == null) {
                throw new IllegalArgumentException("Flight with id " + flight.id + " does not exist");
            }
            flights.put(flight.id, flight);
            return flight;
        }));
    }

    /**
     * Delete flight
     *
     * @param id flight id
     * @throws IllegalArgumentException if flight with given id does not exist
     */
    public Uni<Void> deleteFlight(Long id) {
        return Uni.createFrom().item(Unchecked.supplier(() -> {
            if (flights.get(id) == null) {
                throw new IllegalArgumentException("Flight with id " + id + " does not exist");
            }
            flights.remove(id);
            return null;
        }));
    }

    /**
     * Delete all flights
     */
    public Uni<Void> deleteAllFlights() {
        return Uni.createFrom().item(() -> {
            flights.clear();
            return null;
        });
    }

    /**
     * Cancel flight
     *
     * @param id flight id
     * @throws IllegalArgumentException if flight with given id does not exist
     */
    public Uni<Void> cancelFlight(Long id) {
        return Uni.createFrom().item(Unchecked.supplier(() -> {
            if (flights.get(id) == null) {
                throw new IllegalArgumentException("Flight with id " + id + " does not exist");
            }
            flights.get(id).status = FlightStatus.CANCELLED;
            var response = flightCancellationStub.cancelFlight(
                    FlightCancellationRequest.newBuilder()
                            .setId(Math.toIntExact(id))
                            .setReason("Unknown")
                            .build()
            ).await().indefinitely();

            if (response.getStatus() != FlightCancellationResponseStatus.Cancelled) {
                throw new RuntimeException("Flight cancellation failed");
            }
            return null;
        }));
    }
}
