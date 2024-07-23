package report;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import java.util.List;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import io.smallrye.mutiny.Uni;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;
import org.hibernate.reactive.mutiny.Mutiny;

@Path("/orders")
@Tag(name = "Report Resource", description = "API for managing students")
public class ReportResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all orders", description = "Retrieves all orders in the system")
    public Uni<List<Order>> getAllOrders() {
        return Order.findAllOrders();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get orders by order id", description = "Retrieves the order by its id")
    public Uni<Order> getOrderById(@RestPath Long id) {
        Uni<Order> order = Order.findOrderById(id);
        return order.onItem().ifNull()
                .failWith(() -> new WebApplicationException("Order doesn't exists", Response.Status.NOT_FOUND));
    }

    /**
     * An Endpoint that deletes a student from the database if it exists.
     * @param id The id of the student to be deleted
     * @return Response a 204 response indicating it completed or a 404 if not found.
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @WithTransaction
    @Operation(summary = "Delete order by ID", description = "Deletes a order by the order's ID")
    public Uni<RestResponse<Void>> deleteOrder(@RestPath Long id) {
        return Order.findOrderById(id)
                .onItem().ifNull()
                .failWith(() -> new WebApplicationException("Order doesn't exist", Response.Status.NOT_FOUND))
                .onItem().transformToUni(existingStudent -> {
                    return existingStudent.delete()
                            .replaceWith(() -> RestResponse.noContent());
                });
    }
}
