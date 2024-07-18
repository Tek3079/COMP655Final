package report;

import jakarta.enterprise.context.ApplicationScoped;

import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.reactive.messaging.annotations.Blocking;

/**
 * A bean consuming data from the "order-request" RabbitMQ queue and responding with whether the proper list.
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
    @Blocking
    public String processGpa40(JsonObject orderRequest) throws InterruptedException {

        Order order = orderRequest.mapTo(Order.class);
        order.setTimeToNow();
        Order createdOrder = Order.persistOrder(order);

        return String.format("%s", createdOrder.id);
    }

}