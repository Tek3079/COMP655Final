package ProductService.acme;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

import com.example.purchase.Product;

@Path("/")
@ApplicationScoped
public class ProductResource {
@Path("/products")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Uni<Response> getProducts() {
                return ProductEntity.findAllProducts()
                                .onItem().transform(products -> Response.ok(products).build());
        }

        @Path("/product")
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Uni<Response> createProduct(@Valid ProductEntity product) {
                return ProductEntity.persistProduct(product)
                                .onItem().transform(createdProduct -> Response
                                                .status(Response.Status.CREATED)
                                                .entity(createdProduct)
                                                .build())
                                .onFailure().recoverWithItem(e -> {
                                        e.printStackTrace(); // Log the error for debugging
                                        return Response.status(Response.Status.BAD_REQUEST)
                                                        .entity("Validation Error")
                                                        .build();
                                });
        }

        @GET
        @Path("/product/{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Uni<Response> getProductById(@PathParam("id") Long id) {
                return ProductEntity.getProductById(id)
                                .onItem().transform(product -> {
                                        if (product != null) {
                                                return Response.ok(product).build();
                                        } else {
                                                return Response.status(Response.Status.NOT_FOUND)
                                                                .entity("Product with this ID not found").build();
                                        }
                                });
        }

        @GET
        @Path("/product/random")
        @Produces(MediaType.APPLICATION_JSON)
        public Uni<Response> getRandomProduct() {
                return ProductEntity.findRandomProduct()
                                .onItem().transform(product -> {
                                        if (product != null) {
                                                return Response.ok(product).build();
                                        } else {
                                                return Response.status(Response.Status.NOT_FOUND)
                                                                .entity("No random product found").build();
                                        }
                                });
        }

        @PUT
        @Path("/product/{id}")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Uni<Response> updateProduct(@PathParam("id") Long id, @Valid ProductEntity product) {
                return ProductEntity.updateProduct(id, product)
                                .onItem().transform(updatedProduct -> {
                                        if (updatedProduct != null) {
                                                return Response.ok(updatedProduct).build();
                                        } else {
                                                return Response.status(Response.Status.NOT_FOUND)
                                                                .entity("Product with this ID not found").build();
                                        }
                                });
        }

        @DELETE
        @Path("/product/{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Uni<Response> deleteProduct(@PathParam("id") Long id) {
                return ProductEntity.deleteProduct(id)
                                .onItem().transform(deleted -> {
                                        if (Boolean.TRUE.equals(deleted)) {
                                                return Response.noContent().build();
                                        } else {
                                                return Response.status(Response.Status.NOT_FOUND)
                                                                .entity("Product with this ID not found").build();
                                        }
                                });
}
}