package purchase.org;

import java.util.UUID;

import com.example.customer.Customer;
import com.example.customer.CustomerResponse;
import com.example.customer.CustomerServiceGrpc;
import com.example.purchase.ProductResponse;
import com.example.purchase.ProductServiceGrpc;
import com.google.protobuf.Empty;
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
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import jakarta.ws.rs.core.Response;

@Path("/purchase")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationPath("/purchase")
@Transactional
public class PurchaseResources {
    @Inject
    @GrpcClient("customer")
    CustomerServiceGrpc.CustomerServiceBlockingStub customerService;

    @GrpcClient("product")
    ProductServiceGrpc.ProductServiceBlockingStub productService;

    @Inject
    @Channel("order-request")
    Emitter<Order> orderEmitter;

     @Inject
     @Channel("order-response")
     Multi<Order> orders;

    CustomerResponse customer;
    ProductResponse product;

    @POST
    public Response createPurchase() {
        // Get a random customer
        customer = customerService.getRandomCustomer(Empty.newBuilder().build());
        double balance = customer.getCustomer().getBalance();
        // Try to purchase a product up to 3 times
        for (int i = 0; i < 3; i++) {
            // Get a random product
            product = productService.getRandomProduct(Empty.newBuilder().build());
            Double price = product.getProduct().getPrice();
            // Check if customer has enough balance
            if (customer.getCustomer().getBalance() >= product.getProduct().getPrice()) {
                // Create an order
                Order order = new Order();
//                order.id = UUID.randomUUID().toString();
                order.customerId = customer.getCustomer().getId();
                order.productId = product.getProduct().getId();
                order.amount = product.getProduct().getPrice();
                order.setTimeToNow();

                // Send order to Report service via RabbitMQ
                orderEmitter.send(order);
                customerService
                        .updateCustomer(Customer.newBuilder().setId(customer.getCustomer().getId())
                                .setBalance(balance - price).build());
                return Response.ok(order).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("No suitable product found for the customer").build();
    }

     @GET
     @Path("/order-response")
     @Produces(MediaType.SERVER_SENT_EVENTS)
     public Multi<Order> consume() {
     return orders;
     }

}
