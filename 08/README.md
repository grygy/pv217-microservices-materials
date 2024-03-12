# 08 - Health, metrics, and monitoring

## Health checks

Health checks are critical for cloud application to monitor the state of the application. They are used to check if the application is running, if it is ready to serve requests, and if it is connected to the database, kafka, or other services.

For example Kubernetes uses health checks to determine if a container is running and ready to serve requests, if should be restarted, or if it should be removed and replaced with a new one.

It's also useful for monitoring tools to check the state of the application and send alerts if something is wrong, and you need to take action.

### What should be checked?

- If the service is running.
- If the database is connected.
- If other dependencies are connected.
- Check if external services are available.

#### Types

1. Startup -- If all necessary configurations are loaded.
2. Liveness -- If live probe fails that means the service is not running, cannot be recovered, and should be restarted.
3. Readiness -- If the application is ready to serve requests. Dependencies, database, external services are connected. If the readiness probe fails, the service is not yet ready to serve traffic.

### SmallRye Health

SmallRye Health is an implementation of MicroProfile Health. It provides a way to check the state of the application. It provides a way to check the state of the application. It simplifies the process of creating health checks and provides a way to check the state of the application due to the built-in health checks.

1. `HealthCheck` interface -- Health checks are implemented as classes that implement the `HealthCheck` interface.
2. `@Startup` annotations -- Option for slow starting containers to delay an invocation of the liveness check.
3. `@Liveness` annotations -- Checks if the service is running.
4. `@Readiness` annotations -- Checks if the service is connected to the database/kafka,... without any additional code and is ready to serve requests.




## State of the project

- TODO

## Tasks

### 0. Running docker

Install [Docker desktop](https://docs.docker.com/desktop/) or other docker client.

### 1. Add health check to `passenger-service`

In `passenger-service` you will implement custom readiness health check to check if the baggage service is ready.

#### 1.1. Add `smallrye-health` extension to `passenger-service`

Add `smallrye-health` extension to `passenger-service` by running add extension command.

#### 1.2. Implement `Readiness` check

Go to health package in `passenger-service` and implement `BaggageServiceHealthCheck` class. This class should implement `AsyncHealthCheck` interface and check readiness of the baggage service. 

In `BaggageClientResource` you can find new method `readinessCheck` which should be used to check if the baggage service is ready.

Check if the response `String` from `readinessCheck` contains `"DOWN"` and return `HealthCheckResponse.down` if it does. Otherwise, return `HealthCheckResponse.up`. 

#### 1.3. Test it

Run the service and go to http://localhost:8078/q/health/ready. You should see the status of the health checks including status of the baggage service.

### X. Submit the solution

[//]: # (TODO after setting up github classroom)

## Hints

- TODO

## Troubleshooting

- Check if your docker engine is running.

## Further reading

- https://quarkus.io/guides/smallrye-health
- 