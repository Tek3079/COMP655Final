package report;

import java.util.List;
import java.time.LocalTime;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.util.Random;
import io.quarkus.panache.common.Sort;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

/**
 * Example JPA entity defined as a Panache Entity.
 * An ID field of Long type is provided, if you want to define your own ID field extends <code>PanacheEntityBase</code> instead.
 *
 * This uses the active record pattern, you can also use the repository pattern instead:
 * .
 *
 * Usage (more example on the documentation)
 *
 * {@code
 *     public void doSomething() {
 *         MyEntity entity1 = new MyEntity();
 *         entity1.field = "field-1";
 *         entity1.persist();
 *
 *         List<MyEntity> entities = MyEntity.listAll();
 *     }
 * }
 */

@Table(name="orders", schema="public")
@Entity(name = "orders")
public class Order extends PanacheEntity {

    @Column(name="customer_id")
    @NotBlank(message="Customer Id may not be blank")
    public Integer customerId;

    @Column(name="product_id")
    @NotBlank(message="Product Id may not be blank")
    public Integer productId;

    @Column(name="time")
    public LocalTime orderTime;

    @Column(name="amount")
    @NotBlank(message="Amount may not be blank")
    public float amount;

    public Order(){};

    /**
     * Create an Order in the database. .
     *
     * @param order A order
     * @return The order that was created
     */
    public static Order persistOrder(@Valid Order order) {
        order.persist();
        return order;
    }

    /**
     * Find a Order by the order's ID in the database. ID is a primary key, so there can only be 1 result.
     * @param id The ID of the order
     * @return The order containing the ID in the param
     */
    public static Order findOrderById(Long id) {
        return find("id", id).firstResult();
    }

    /**
     * A method to return all the orders in the psql database.
     * @return a List of Orders that are in the database
     */
    public static List<Order> findAllOrders() {
        return Order.listAll(Sort.by("id"));
    }

    /**
     * A method that deletes a order in the database by the id of the order. It takes an ID and deletes the order.
     * @param id The id of the order in the database
     */
    public static void deleteOrder(Long id) {
        delete("id", id);
    }

    public void setTimeToNow() {
        this.orderTime = LocalTime.now();;
    }
}
