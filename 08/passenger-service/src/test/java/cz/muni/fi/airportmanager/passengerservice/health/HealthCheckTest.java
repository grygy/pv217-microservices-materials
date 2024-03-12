package cz.muni.fi.airportmanager.passengerservice.health;


import cz.muni.fi.airportmanager.passengerservice.client.BaggageClientResource;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


@QuarkusTest
class HealthCheckTest {

    @InjectMock
    @RestClient
    BaggageClientResource baggageClientResource;


    @Test
    void testServiceShouldBeReady() {
        Mockito.when(this.baggageClientResource.readinessCheck()).thenReturn(Uni.createFrom().item("UP"));

        given()
                .when().get("/q/health/ready")
                .then()
                .statusCode(200)
                .body("status", is("UP"));
    }

    @Test
    void testServiceShouldNotBeReady() {
        Mockito.when(this.baggageClientResource.readinessCheck()).thenReturn(Uni.createFrom().item("DOWN"));

        given()
                .when().get("/q/health/ready")
                .then()
                .statusCode(503)
                .body("status", is("DOWN"));
    }
}