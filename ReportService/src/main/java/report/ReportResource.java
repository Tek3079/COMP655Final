package report;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestPath;

@Path("/orders")
@Tag(name = "Report Resource", description = "API for managing students")
public class ReportResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Get all orders", description = "Retrieves all orders in the system")
    public Response getAllOrders() {
        return Response.ok(Order.findAllOrders()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Get orders by order id", description = "Retrieves the order by its id")
    public Response getOrderById(@RestPath Long id) {
        Order order = Order.findOrderById(id);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(order).build();
        }
    }

    /**
     * An Endpoint that deletes a student from the database if it exists.
     * @param id The id of the student to be deleted
     * @return Response a 204 response indicating it completed or a 404 if not found.
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    @Operation(summary = "Delete order by ID", description = "Deletes a order by the order's ID")
    public Response deleteOrder(@RestPath Long id) {
        Order entity = Order.findOrderById(id);
        if(entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Order.deleteOrder(id);
        return Response.noContent().build();
    }
}
