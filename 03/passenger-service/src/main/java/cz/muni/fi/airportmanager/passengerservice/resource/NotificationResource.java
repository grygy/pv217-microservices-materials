package cz.muni.fi.airportmanager.passengerservice.resource;

import cz.muni.fi.airportmanager.passengerservice.model.Notification;
import cz.muni.fi.airportmanager.passengerservice.service.NotificationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/notification")
@Tag(name = "Notification", description = "Notification API")
public class NotificationResource {

    @Inject
    NotificationService notificationService;

    /**
     * Get list of all notifications
     *
     * @return list of all notifications
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<List<Notification>> hello() {
        return RestResponse.status(Response.Status.OK, notificationService.listAll());
    }

    /**
     * Delete all notifications
     */
    @DELETE
    public RestResponse<Void> deleteAll() {
        notificationService.deleteAll();
        return RestResponse.status(Response.Status.NO_CONTENT);
    }


}
