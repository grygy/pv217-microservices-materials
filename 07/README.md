# 07 - Messaging and Kafka

## Messaging

TODO

## Kafka

TODO

## Zookeeper

TODO

## State of the project

- In the `passenger-service` `PassengerService` class, we have a new method `addNotificationForPassenger` that creates a new `Notification` for the passenger.

## Tasks

### 0. Running docker

Install [Docker desktop](https://docs.docker.com/desktop/) or other docker client.

### 1. Introduction to task

Right now, we have a simple notification system in the `passenger-service` that handles notification about the flight cancellation. Now, we want to add a new feature that will also notify the passengers about the baggage status change. We want to use Kafka to handle communication between the `passenger-service` and the `baggage-service`. 

### 2. Add Kafka producer to the `baggage-service`

#### 2.1. Prepare `BaggageStateChange` class

Prepare a new class `BaggageStateChange` in the `baggage-service` that will be the payload of the message. The class should contain the following fields:
- `baggageId` - the id of the baggage
- `passengerId` - the id of the passenger
- `newState` - the new state of the baggage

#### 2.2. Add Baggage State Change producer

Prepare a new class `BaggageStateChangeProducer` in the `baggage-service` that will be responsible for sending the `BaggageStateChange` message to the Kafka `baggage-state-change` topic. 

Inject `Emitter` with `@Channel` annotation to publish messages to the `baggage-state-change` topic in the `send` method.

#### 2.3. Configure Kafka serialization

Because we are sending an object, we need to configure the serialization of the messages. Add the following properties to the `application.properties` file in `baggage-service`:

```properties
mp.messaging.outgoing.baggage-state-change.value.serializer=io.quarkus.kafka.client.serialization.ObjectMapperSerializer
```

#### 2.4. Connect the `BaggageStateChangeProducer` with the `BaggageService`

When the state change we want to send the message as well. Go to the `BaggageService` and inject the `BaggageStateChangeProducer` and call the `send` method when the baggage state changes.

### 3. Add Kafka consumer to the `passenger-service`

#### 3.1. Copy Baggage State Change class to the `passenger-service`

Copy the `BaggageStateChange` class along with the `BaggageStatus` enum to the `passenger-service` to `/kafka/model` package.

#### 3.2. Add Baggage State Change processor

Now, we need to process the messages incoming from the `baggage-service`. Prepare a new class `BaggageStateChangeProcessor` in the `passenger-service` that will be responsible for processing the `BaggageStateChange` message from the Kafka `baggage-state-change` topic.

This class should accept the `@Incomming` requests from the `baggage-state-change` topic, process the message, and create a new `Notification` for the passenger.

### 4. Test the communication

Thanks to the Quarkus Dev Services, we can easily test the communication between the services using the Kafka. Dev services will start the Kafka broker for us. We will prepare it for production later.

Scenario:
1. Start the `baggage-service` and `passenger-service` in dev mode.
2. Create a new passenger.
3. Create new baggage for the passenger.
4. Change the state of the baggage as claimed.
5. Get the notifications and see if both check-in and claimed notifications are present.

### 5. Prepare the application for production

Kill the dev services and prepare the application for production.

#### 5.1. Add Zookeeper to the docker-compose

Add the following service to the `docker-compose.yaml` file:

```yaml
  zookeeper:
    image: quay.io/strimzi/kafka:0.39.0-kafka-3.6.1
    command: [
      "sh", "-c",
      "bin/zookeeper-server-start.sh config/zookeeper.properties"
    ]
    ports:
      - "2181:2181"
    environment:
      LOG_DIR: /tmp/logs
    networks:
      - app-network
```

#### 5.2. Add Kafka to the docker-compose

Add the following service to the `docker-compose.yaml` file:

```yaml
    kafka:
    image: quay.io/strimzi/kafka:0.39.0-kafka-3.6.1
    command: [
      "sh", "-c",
      "bin/kafka-server-start.sh config/server.properties --override listeners=$${KAFKA_LISTENERS} --override advertised.listeners=$${KAFKA_ADVERTISED_LISTENERS} --override zookeeper.connect=$${KAFKA_ZOOKEEPER_CONNECT}"
    ]
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      LOG_DIR: "/tmp/logs"
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:9092
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    networks:
      - app-network
```

#### 5.3. Add environment variables for `passenger-service` and `baggage-service`

Now, we need to add the environment variables for the `passenger-service` and `baggage-service` for them to be able to connect to the Kafka broker. In the `docker-compose.yaml` file, add the following environment variables to the `passenger-service` and `baggage-service`:

```yaml
    environment:
      KAFKA_BOOTSTRAP_SERVERS: kafka:9092
```

#### 5.4. Build and run the application

```bash
cd passenger-service && mvn package && cd .. && cd baggage-service && mvn package && cd .. && cd flight-service && mvn package && cd .. && docker compose build && docker compose up
```

#### 5.5. Test the application

Test the application as in the previous step.

### X. Submit the solution

[//]: # (TODO after setting up github classroom)

## Hints

- TODO

## Troubleshooting

- Check if your docker engine is running.

## Further reading

- https://quarkus.io/guides/kafka-reactive-getting-started
- https://quarkus.io/guides/kafka