package purchase;

import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.resteasy.reactive.RestSseElementType;

import com.example.purchase.CustomerResponse;
import com.example.purchase.CustomerServiceGrpc;
import com.example.purchase.ProductResponse;
import com.example.purchase.ProductServiceGrpc;
import com.google.protobuf.Empty;
import java.util.UUID;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/purchase")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationPath("")
@Transactional
public class PurchaseResource {

    @GrpcClient("customer")
    CustomerServiceGrpc.CustomerServiceBlockingStub customerClient;

    @GrpcClient("product")
    ProductServiceGrpc.ProductServiceBlockingStub productClient;

    @Inject
    @Channel("order-request")
    Emitter<Order> orderEmitter;

    @Channel("order-response")
    Emitter<String> orderResponseEmitter;
    @Inject
    @Channel("responses")
    Multi<String> responses;

    @POST
    public Response createPurchase() {
        // Get a random customer
        CustomerResponse customer = customerClient.getRandomCustomer(Empty.newBuilder().build());

        // Try to purchase a product up to 3 times
        for (int i = 0; i < 3; i++) {
            // Get a random product
            ProductResponse product = productClient.getRandomProduct(Empty.newBuilder().build());

            // Check if customer has enough balance
            if (customer.getCustomer().getBalance() >= product.getProduct().getPrice()) {
                // Create an order
                Order order = new Order();
                order.id = UUID.randomUUID().toString();
                order.customerId = customer.getCustomer().getId();
                order.productId = product.getProduct().getId();
                order.amount = product.getProduct().getPrice();
                order.setTimeToNow();

                // Send order to Report service via RabbitMQ
                orderEmitter.send(order);
                return Response.ok(order).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("No suitable product found for the customer").build();
    }

    @GET
    @Path("/response")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestSseElementType(MediaType.TEXT_PLAIN)
    public Multi<String> consume() {
        return responses;
    }
}
