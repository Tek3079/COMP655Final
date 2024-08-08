package report;

import java.util.List;
import java.time.LocalDateTime;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.util.Random;
import io.quarkus.panache.common.Sort;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import io.smallrye.mutiny.Uni;

/**
 *
 * .This is the Order Object for the Report Service. It is reactive and consists of
 * and Order ID, Customer ID, and Purchase ID and Amount all received from the Purchase Service.
 * The OrderProcessor sets the time of the transaction, records it in Postgres, and returns the object.
 */

@Table(name = "orders", schema = "public")
@Entity(name = "orders")
public class Order extends PanacheEntity {

    @Column(name = "order_id")
    @NotBlank(message = "Order Id may not be blank")
    public String orderId;

    @Column(name = "customer_id")
    @NotBlank(message = "Customer Id may not be blank")
    public Integer customerId;

    @Column(name = "product_id")
    @NotBlank(message = "Product Id may not be blank")
    public Integer productId;

    @Column(name = "time")
    public LocalDateTime orderTime;

    @Column(name = "amount")
    @NotBlank(message = "Amount may not be blank")
    public float amount;

    public Order() {
    };

    public Order(Integer customerId, Integer productId, float amount) {
        this.customerId = customerId;
        this.productId = productId;
        this.amount = amount;
        this.orderTime = LocalDateTime.now();
    }


    /**
     * Find a Order by the order's ID in the database. ID is a primary key, so there
     * can only be 1 result.
     *
     * @param id The ID of the order
     * @return The order containing the ID in the param
     */
    public static Uni<Order> findOrderById(Long id) {
        return find("id", id).firstResult();
    }

    /**
     * A method to return all the orders in the psql database.
     *
     * @return a List of Orders that are in the database
     */
    public static Uni<List<Order>> findAllOrders() {
        return Order.listAll();
    }

    /**
     * A method that deletes a order in the database by the id of the order. It
     * takes an ID and deletes the order.
     *
     * @param id The id of the order in the database
     */
    public static void deleteOrder(Long id) {
        delete("id", id);
    }

    public void setTimeToNow() {
        this.orderTime = LocalDateTime.now();
        ;
    }
}