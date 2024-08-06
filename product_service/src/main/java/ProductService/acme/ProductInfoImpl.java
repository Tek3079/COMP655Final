
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
}