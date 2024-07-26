import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List; // Importing List from java.util

@Table(name = "products", schema = "public")
@Entity(name = "products")
@RegisterForReflection
public class ProductEntity extends PanacheEntity {

    @Column(name="name", nullable = false)
    @NotBlank(message = "name cannot be blank")
    public String name;

    @Column(name="quantity", nullable = false)
    @NotNull(message = "Quantity cannot be null")
    @Min(message="Cannot be lower than 0", value = 0)
    public Integer quantity;

    @Column(name="price", nullable = false)
    @NotNull(message = "Price cannot be null")
    @Min(message="Cannot be lower than 0", value = 0)
    public Float price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public static List<ProductEntity> findAllProducts() {
        return listAll();
    }

    public static void persistProduct(ProductEntity product) {
        product.persist();
    }

    public static ProductEntity getProductById(Long id) {
        return findById(id);
    }

    public static ProductEntity findRandomProduct() {
        return find("order by random()").firstResult();
    }

    public static ProductEntity updateProduct(Long id, ProductEntity product) {
        ProductEntity pro = findById(id);
        if (pro != null) {
            pro.name = product.name;
            pro.quantity = product.quantity;
            pro.price = product.price;
            pro.persist();
            return pro;
        }
        return null;
    }

    public static void deleteProduct(Long id) {
        deleteById(id);
    }
}
