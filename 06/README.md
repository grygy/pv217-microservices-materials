# 06 - RestClient, Containerization

## RestClient

TODO

## Containerization

TODO

### Docker

TODO

### Docker compose

TODO

## Tasks

### 0. Running docker

Install [Docker desktop](https://docs.docker.com/desktop/) or other docker client. Our test database will run in docker container.

### 1. Implement BaggageClient

#### 1.1. Check Baggage service API

- Run `baggage-service` and go to `http://localhost:8077/swagger-ui/` and check the baggage service API. You should be able to see the API documentation.

- Focus on `GET /baggage/passenger/{passengerId}` endpoint. It returns baggage for passenger with given id. We will use this endpoint in `BaggageService` in `passenger-service`.

#### 1.2. Implement `BaggageService`

TODO

#### 1.3. Use `BaggageService` in `PassengerService`

TODO

### 5. Verify if everything is working

TODO

### X. Submit the solution

[//]: # (TODO after setting up github classroom)

## Hints

- You can inspire yourself from tests in `flilght-service`.
- Create helper methods in tests for create example object.

## Troubleshooting

- Check if your docker engine is running.

## Further reading

- https://quarkus.io/guides/getting-started-testing