# 06 - RestClient, Containerization

## RestClient

TODO

## Containerization

TODO

### Docker

TODO

### Docker compose

TODO

## State of the project

- There is a new `baggage-service` is a REST service for tracking baggage. With this service 

## Tasks

### 0. Running docker

Install [Docker desktop](https://docs.docker.com/desktop/) or other docker client. Our test database will run in docker
container.

### 1. Define `BaggageClientResource` in `passenger-service`

TODO

### 2. Create docker-compose file for baggage-service

#### 2.1. Set application properties

Right now the `baggage-service` REST service is running on port 8077. Let's overrride it with `%prod.` prefix for
production profile to run it on port 8080. Dockerfile.jvm is expecting the application to run on port 8080. We will then
let `docker-compose` to handle the port back to 8077.

#### 2.2. Package the application

We need to create a jar file for the `baggage-service` to be able to run it in a docker container. Run the following
command in `baggage-service` directory:

```bash
./mvnw package
```

It should create a `target/quarkus-app` directory with the jar file.

#### 2.3. Run pure docker container

Now try to run the `baggage-service` in a docker container. We already have Dockerfile.jvm created for us by quarkus
in `./src/main/docker` directory.

1. Build the docker image:

   ```bash
   docker build -f src/main/docker/Dockerfile.jvm -t quarkus/baggage-service-jvm .
   ```

2. Run the docker container:
    - Don't forget to kill the running `baggage-service` otherwise the port 8077 will be already in use.

   ```bash
   # Maps the port 8080 from the container to the port 8077 on the host
   docker run -i --rm -p 8077:8080 quarkus/baggage-service-jvm
   ```

3. Go to http://localhost:8077/q/swagger-ui and check if the service is running.

#### 2.4. Create docker compose configuration

In `baggage-service` go to `docker-compose.yml` file and create a configuration for baggage-service.

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