package cz.muni.fi;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/hello")
public class GreetingResource {

    @ConfigProperty(name = "quarkus.http.port")
    int port;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello on " + port;
    }
}
