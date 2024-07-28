import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid; 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import io.smallrye.mutiny.Uni;

@Table(name = "products", schema = "public")
@Entity(name = "products")
@RegisterForReflection
public class ProductEntity extends PanacheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

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

    // Getter and Setter methods
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

    public static Uni<List<ProductEntity>> findAllProducts() {
        return listAll();
    }

    public static Uni<Void> persistProduct(ProductEntity product) {
        return product.persist().replaceWithVoid();
    }

    public static Uni<ProductEntity> getProductById(Long id) {
        return findById(id);
    }

    public static Uni<ProductEntity> findRandomProduct() {
        return find("order by random()").firstResult();
    }

    public static Uni<ProductEntity> updateProduct(Long id, @Valid ProductEntity newProduct) {
        return findById(id).onItem().transformToUni(existingProduct -> {
            if (existingProduct != null) {
                existingProduct.name = newProduct.name;
                existingProduct.quantity = newProduct.quantity;
                existingProduct.price = newProduct.price;
                return existingProduct.persist().replaceWith(existingProduct);
            }
            return Uni.createFrom().nullItem();
        });
    }

    public static Uni<Boolean> deleteProduct(Long id) {
        return deleteById(id);
    }
}
