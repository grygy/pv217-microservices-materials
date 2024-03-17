# 09 - Fault tolerance and tracing

## Fault tolerance

In distributed microservices systems, failures are inevitable. The network is unreliable, services can be slow, and the
system can be under heavy load. To build resilient systems, we need to handle these failures gracefully.

In this lecture we will use the SmallRye Fault Tolerance library which implements the MicroProfile Fault Tolerance
specification. This library provides a set of annotations to handle these challenges and build more resilient systems.

### SmallRye Fault Tolerance annotations

- `@Timeout` -- It defines a timeout for a method. If the method takes longer than the specified time,
  a `TimeoutException` is thrown. It's useful for preventing a method from taking too long to execute and possibly
  giving user a faster feedback.
- `@Retry` -- It specifies that a method should be executed again if it fails. You can specify the maximum number of
  retries and the delay between retries. It's useful for handling transient failures.
- `@CircuitBreaker` -- It specifies that a method should be executed in a circuit breaker. If the method fails, the
  circuit is opened, and the method is not executed for a specified time. After that time, the circuit is half-opened,
  and the method is executed again. If it fails, the circuit is opened again. If it succeeds, the circuit is closed
  again. It's useful for not overloading a failing service.
- `@Fallback` -- It specifies a fallback method that should be executed if the original method fails. It's useful for
  providing a default value (eg. default recommendations) or a default behavior when a method fails.

#### Example

```java
public class HelloResource {

    @GET
    @Path("/hello")
    @Timeout(1000)
    public String hello() {
        // calling an external service for e.g. name
    }

    @GET
    @Path("/hello")
    @Retry(maxRetries = 3, delay = 1000)
    public String hello() {
        // calling an external service for e.g. name
    }

    @GET
    @Path("/hello")
    @CircuitBreaker(
            requestVolumeThreshold = 4, // number of requests before the circuit is opened 
            failureRatio = 0.75,  // the failure ratio at which the circuit should open
            delay = 1000,  // the time the circuit should be opened before it's half-opened
            successThreshold = 2  // the number of successful requests before the circuit is closed again
    )
    public String hello() {
        // calling an external service for e.g. name
    }

    @GET
    @Path("/hello")
    @Fallback(fallbackMethod = "fallbackHello")
    public String hello() {
        // calling an external service for e.g. name
    }

    public String fallbackHello() {
        return "Hello, fallback!";
    }
}
```

## Tracing

TODO

## State of the project

- In `baggage-service`, there is artificial failure in `BaggageResource.getBaggageByPassengerId` method.

## Tasks

### 1. Add fault tolerance to the `passenger-service`

#### 1.1. In `passenger-service`, go to `PassengerResource` and follow the TODOs

Add a `@Timeout` annotation to the `get` method. Set the value to 250 milliseconds.

#### 1.2. In `passenger-service`, add `@Retry` annotation to the `getPassengerWithBaggage` in `PassengerResource`.

Baggage service has 50 % of failure to retrieve baggage by passenger id.

1. Create a passenger and baggage for the passenger.
2. Try to retrieve the passenger with baggage using `/passenger/{passengerId}/baggage` endpoint.
   1. It should fail 50 % of the time.
3. Add a `@Retry` annotation to the `getPassengerWithBaggage` method in `PassengerResource`. Set the maximum number of retries to 4 and the delay between retries to 500 ms.
4. Try again to retrieve the passenger with baggage using `/passenger/{passengerId}/baggage` endpoint.
   1. It should almost always succeed. Note the delay of the response meaning the baggage service failed and the retry was executed.

### 2. Add fault tolerance to the `flight-service`

#### 2.1. In `flight-service`, go to `FlightService` and follow the TODOs
 
Add a `@CircuitBreaker` annotation to the `cancelFlight` method.

### 3. TODO

TODO

### X. Submit the solution

[//]: # (TODO after setting up github classroom)

## Hints

- Build and run docker
  ```bash
  cd passenger-service && mvn package && cd .. && cd baggage-service && mvn package && cd .. && cd flight-service && mvn package && cd .. && docker compose build && docker compose up
   ```

## Troubleshooting

- Check if your docker engine is running.

## Further reading

- https://quarkus.io/guides/smallrye-fault-tolerance#adding-resiliency-retries
