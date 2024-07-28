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
    public Uni<List<ProductEntity>> getAllProducts() {
        return ProductEntity.findAllProducts();
    }

    /**
     * Create a new product.
     *
     * @param product ProductEntity object to be created.
     * @return The created ProductEntity.
     */
    @Transactional
    public Uni<ProductEntity> createProduct(@Valid ProductEntity product) {
        return ProductEntity.persistProduct(product).replaceWith(product);
    }

    /**
     * Retrieve a product by its ID.
     *
     * @param id ID of the product to be retrieved.
     * @return The ProductEntity with the given ID, or null if not found.
     */
    public Uni<ProductEntity> getProductById(Long id) {
        return ProductEntity.getProductById(id);
    }

    /**
     * Retrieve a random product.
     *
     * @return A random ProductEntity, or null if none exists.
     */
    public Uni<ProductEntity> getRandomProduct() {
        return ProductEntity.findRandomProduct();
    }

    /**
     * Update an existing product.
     *
     * @param id ID of the product to be updated.
     * @param product Updated product details.
     * @return The updated ProductEntity, or null if not found.
     */
    @Transactional
    public Uni<ProductEntity> updateProduct(Long id, @Valid ProductEntity product) {
        return ProductEntity.updateProduct(id, product);
    }

    /**
     * Delete a product by its ID.
     *
     * @param id ID of the product to be deleted.
     */
    @Transactional
    public Uni<Boolean> deleteProduct(Long id) {
        return ProductEntity.deleteProduct(id);
    }
}
