import java.net.URI;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestPath;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@Tag(name = "Products", description="Operations on the product")
public class ProductResource {

    /*
     * Get all the products and return it as JSON
     */
    @Path("/products")
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts() {
        return Response.ok(ProductEntity.findAllProducts()).build();
    }

    /*
     * Create a new product in the database
     */
    @Path("/product")
    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(@Valid ProductEntity product) {
        try {
            ProductEntity.persistProduct(product);
            return Response.created(URI.create("/product/id" + product.id)).entity(product).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("Validation Error").build();
        }
    }

    /*
     * Get product by ID and return JSON format
     */
    @GET
    @Path("/product/{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductById(@RestPath Long id) {
        ProductEntity product = ProductEntity.getProductById(id);
        if(product != null) {
            return Response.ok(product).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).entity("Product with this ID not found").build();
        }
    }


    /*
     * Get a random product from the database and return JSON format
     */
    @GET
    @Path("/product/random")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRandom() {
        ProductEntity product = ProductEntity.findRandomProduct();
        if(product != null) {
            return Response.ok(product).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).entity("Product with this ID not found").build();
        }
    }

    /*
     * Get product ID and new product detail
     * Update the product with ID provided
     */
    @PUT
    @Path("/product/{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(@RestPath Long id, @Valid ProductEntity product) {
        ProductEntity updatedProduct = ProductEntity.updateProduct(id, product);
        if(product != null) {
            return Response.ok(updatedProduct).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).entity("Product with this ID not found").build();
        }
    }


    /*
     * Delete the product with specified ID
     */
    @DELETE
    @Path("/product/{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@RestPath Long id) {
        ProductEntity product = ProductEntity.getProductById(id);
        if(product != null) {
            ProductEntity.deleteProduct(id);
            return Response.noContent().build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).entity("Product with this ID not found").build();
        }
    }
}