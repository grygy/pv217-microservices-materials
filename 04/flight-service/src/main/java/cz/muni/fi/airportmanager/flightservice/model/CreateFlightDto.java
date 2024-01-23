package cz.muni.fi.airportmanager.flightservice.model;

import java.util.Date;

public class CreateFlightDto {
    public String name;
    public String airportFrom;
    public String airportTo;
    public Date departureTime;
    public Date arrivalTime;
    public int capacity;
    public FlightStatus status;
}
