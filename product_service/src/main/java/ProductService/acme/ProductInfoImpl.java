package ProductService.acme;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;

@ApplicationScoped
public class ProductInfoImpl {

    /**
     * Retrieve all products.
     *
     * @return List of all products.
     */
    public Uni<List<Product>> getAllProducts() {
        return Product.findAllProducts();
    }

    /**
     * Create a new product.
     *
     * @param product ProductEntity object to be created.
     * @return The created ProductEntity.
     */

    @WithTransaction()
    public Uni<Product> createProduct(@Valid Product product) {
        return Product.persistProduct(product).replaceWith(product);
    }

    /**
     * Retrieve a product by its ID.
     *
     * @param id ID of the product to be retrieved.
     * @return The ProductEntity with the given ID, or null if not found.
     */
    public Uni<Product> getProductById(Long id) {
        return Product.getProductById(id);
    }

    /**
     * Retrieve a random product.
     *
     * @return A random ProductEntity, or null if none exists.
     */
    public Uni<Product> getRandomProduct() {
        return Product.findRandomProduct();
    }

    /**
     * Update an existing product.
     *
     * @param id      ID of the product to be updated.
     * @param product Updated product details.
     * @return The updated ProductEntity, or null if not found.
     */
    @WithTransaction()
    public Uni<Product> updateProduct(Long id, @Valid Product product) {
        return Product.updateProduct(id, product);
    }

    /**
     * Delete a product by its ID.
     *
     * @param id ID of the product to be deleted.
     */

    @WithTransaction()
    public Uni<Boolean> deleteProduct(Long id) {
        return Product.deleteProduct(id);
    }
}