package report;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.Multi;
import io.quarkus.hibernate.reactive.panache.common.*;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.hibernate.reactive.mutiny.Mutiny;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.jboss.resteasy.reactive.RestResponse;


/**
 * A bean consuming data from the "order-request" RabbitMQ queue and responding
 * with whether the proper list.
 * The result is pushed to the "order-response" RabbitMQ exchange.
 */
@ApplicationScoped
public class OrderProcessor {

    @Inject
    Mutiny.SessionFactory sf;
    /**
     *
     * @param orderRequest to be saved
     * @return String with the id
     */
    @Incoming("order-request")
    @Outgoing("order-response")
    public Uni<Order> processOrderRequest(JsonObject orderRequest) {
        Order order = orderRequest.mapTo(Order.class);
        order.setTimeToNow();

        return Panache.withTransaction(order::persist)
                .onItem().transform(persistedOrder -> (Order) persistedOrder)
                .onFailure().recoverWithItem(failure -> {
                    return order;
                });
    }

}