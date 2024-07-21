import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List; // Importing List from java.util

@ApplicationScoped
public class ProductInfoImpl {

    /**
     * Retrieve all products.
     *
     * @return List of all products.
     */
    public List<ProductEntity> getAllProducts() {
        return ProductEntity.findAllProducts();
    }

    /**
     * Create a new product.
     *
     * @param product ProductEntity object to be created.
     * @return The created ProductEntity.
     */
    @Transactional
    public ProductEntity createProduct(@Valid ProductEntity product) {
        ProductEntity.persistProduct(product);
        return product;
    }

    /**
     * Retrieve a product by its ID.
     *
     * @param id ID of the product to be retrieved.
     * @return The ProductEntity with the given ID, or null if not found.
     */
    public ProductEntity getProductById(Long id) {
        return ProductEntity.getProductById(id);
    }

    /**
     * Retrieve a random product.
     *
     * @return A random ProductEntity, or null if none exists.
     */
    public ProductEntity getRandomProduct() {
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
    public ProductEntity updateProduct(Long id, @Valid ProductEntity product) {
        return ProductEntity.updateProduct(id, product);
    }

    /**
     * Delete a product by its ID.
     *
     * @param id ID of the product to be deleted.
     */
    @Transactional
    public void deleteProduct(Long id) {
        ProductEntity.deleteProduct(id);
    }
}
