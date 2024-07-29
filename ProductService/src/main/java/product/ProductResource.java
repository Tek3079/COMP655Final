package product;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/")
public class ProductResource {

    @Inject
    ProductInfoImpl productInfo;

    @Path("/products")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getProducts() {
        return productInfo.getAllProducts()
                .onItem().transform(products -> Response.ok(products).build());
    }

    @Path("/product")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createProduct(@Valid Product product) {
        return productInfo.createProduct(product)
                .onItem().transform(createdProduct -> {
                    return Response.created(URI.create("/product/" + createdProduct.id)).entity(createdProduct).build();
                })
                .onFailure().recoverWithItem(e -> {
                    e.printStackTrace();
                    return Response.status(Response.Status.BAD_REQUEST).entity("Validation Error").build();
                });
    }

    @GET
    @Path("/product/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getProductById(@PathParam("id") Long id) {
        return productInfo.getProductById(id)
                .onItem().transform(product -> {
                    if (product != null) {
                        return Response.ok(product).build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).entity("Product with this ID not found").build();
                    }
                });
    }

    @GET
    @Path("/product/random")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getRandomProduct() {
        return productInfo.getRandomProduct()
                .onItem().transform(product -> {
                    if (product != null) {
                        return Response.ok(product).build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).entity("No random product found").build();
                    }
                });
    }

    @PUT
    @Path("/product/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> updateProduct(@PathParam("id") Long id, @Valid Product product) {
        return productInfo.updateProduct(id, product)
                .onItem().transform(updatedProduct -> {
                    if (updatedProduct != null) {
                        return Response.ok(updatedProduct).build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).entity("Product with this ID not found").build();
                    }
                });
    }

    @DELETE
    @Path("/product/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> deleteProduct(@PathParam("id") Long id) {
        return productInfo.deleteProduct(id)
                .onItem().transform(deleted -> {
                    if (Boolean.TRUE.equals(deleted)) {
                        return Response.noContent().build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).entity("Product with this ID not found").build();
                    }
                });
    }
}
