package cz.muni.fi.model;

import java.util.Date;

public class Flight {
    public int id;
    public String name;
    public String airportFrom;
    public String airportTo;
    public Date departureTime;
    public Date arrivalTime;
    public int capacity;
    public FlightStatus status;
}
