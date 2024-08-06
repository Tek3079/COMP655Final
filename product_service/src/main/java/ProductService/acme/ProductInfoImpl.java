
package ProductService.acme;

import io.quarkus.grpc.GrpcService;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;

import com.example.purchase.Product;
import com.example.purchase.ProductResponse;
import com.example.purchase.ProductServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;


@GrpcService
public class ProductInfoImpl extends ProductServiceGrpc.ProductServiceImplBase {
    @Override
    public void getRandomProduct(Empty request, StreamObserver<ProductResponse> responseObserver) {
        Panache.withSession(() -> 
            ProductEntity.findRandomProduct()
                .onItem().transform(productEntity -> {
                    if (productEntity == null) {
                        return null;
                    }
                    return ProductResponse.newBuilder()
                        .setProduct(Product.newBuilder()
                            .setId(productEntity.id)
                            .setName(productEntity.name)
                            .setQuantity(productEntity.quantity)
                            .setPrice(productEntity.price)
                            .build())
                        .build();
                })
        ).subscribe().with(
            productResponse -> {
                if (productResponse == null) {
                    responseObserver.onError(new RuntimeException("No product found"));
                } else {
                    responseObserver.onNext(productResponse);
                    responseObserver.onCompleted();
                }
            },
            throwable -> responseObserver.onError(new RuntimeException("Failed to fetch product", throwable))
        );
    }
    @Override
    public void updateProduct(Product request, StreamObserver<Empty> responseObserver) {
        // Create a new ProductEntity instance to update with the data from the request
        ProductEntity newProduct = new ProductEntity();
        newProduct.name = request.getName();
        newProduct.quantity = request.getQuantity();
        newProduct.price = request.getPrice();

        // Use Panache.withTransaction to ensure a reactive session is available
        Panache.withTransaction(() -> ProductEntity.updateProduct(request.getId(), newProduct)
                .onItem().transformToUni(updatedProduct -> {
                    // Notify successful completion
                    responseObserver.onNext(Empty.getDefaultInstance());
                    responseObserver.onCompleted();
                    return Uni.createFrom().voidItem(); // Ensure correct return type
                })
                .onFailure().recoverWithUni(throwable -> {
                    // Handle failure
                    responseObserver.onError(new RuntimeException("Failed to update product", throwable));
                    return Uni.createFrom().voidItem(); // Ensure correct return type
                })).subscribe().with(
                // Completion handlers for subscription
                success -> {
                },
                throwable -> responseObserver
                        .onError(new RuntimeException("Failed to update product", throwable)));
    }
}