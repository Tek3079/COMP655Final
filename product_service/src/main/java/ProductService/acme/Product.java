package ProductService.acme;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

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
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import io.smallrye.mutiny.Uni;

@Table(name = "products", schema = "public")
@Entity(name = "products")
@RegisterForReflection
public class Product extends PanacheEntity {

    @Column(name = "name", nullable = false)
    @NotBlank(message = "name cannot be blank")
    public String name;

    @Column(name = "quantity", nullable = false)
    @NotNull(message = "Quantity cannot be null")
    @Min(message = "Cannot be lower than 0", value = 0)
    public Integer quantity;

    @Column(name = "price", nullable = false)
    @NotNull(message = "Price cannot be null")
    @Min(message = "Cannot be lower than 0", value = 0)
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

    public static Uni<List<Product>> findAllProducts() {
        return listAll();
    }

    public static Uni<Void> persistProduct(Product product) {
        return product.persist().replaceWithVoid();
    }

    public static Uni<Product> getProductById(Long id) {
        return findById(id);
    }

    public static Uni<Product> findRandomProduct() {
        return find("order by random()").firstResult();
    }

    public static Uni<Product> updateProduct(Long id, @Valid Product newProduct) {
        return findById(id).onItem().transformToUni(existingProduct -> {
            if (existingProduct != null) {
                // Cast to your actual entity class
                Product product = (Product) existingProduct;
                product.name = newProduct.name;
                product.quantity = newProduct.quantity;
                product.price = newProduct.price;
                return product.persist().replaceWith(product);
            }
            return Uni.createFrom().failure(new NotFoundException("Product not found with id: " + id));
        });
    }

    public static Uni<Boolean> deleteProduct(Long id) {
        return deleteById(id);
    }
}