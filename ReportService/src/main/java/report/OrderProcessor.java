package report;

import jakarta.enterprise.context.ApplicationScoped;
import io.smallrye.mutiny.Uni;
import io.quarkus.hibernate.reactive.panache.common.*;
import jakarta.transaction.Transactional;
import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;


/**
 * A bean consuming data from the "order-request" RabbitMQ queue and responding
 * with whether the proper list.
 * The result is pushed to the "order-response" RabbitMQ exchange.
 */
@ApplicationScoped
public class OrderProcessor {

    /**
     *
     * @param orderRequest to be saved
     * @return String with the id
     * @throws InterruptedException
     */
    @Incoming("order-request")
    @Outgoing("order-response")
    @Transactional
    public String processOrderRequest(JsonObject orderRequest) throws InterruptedException {

        Order order = orderRequest.mapTo(Order.class);
        order.setTimeToNow();
        order.persist();
        order.flush();

        return String.format("%s", order.id);
    }

}