package com.example.customer;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import jakarta.transaction.Transactional;

@GrpcService
public class CustomerServiceImpl extends CustomerServiceGrpc.CustomerServiceImplBase {

    @Override
    public void getRandomCustomer(Empty request, StreamObserver<CustomerResponse> responseObserver) {
        CustomerEntity customerEntity = CustomerEntity.findRandomCustomer();

        Customer customer = Customer.newBuilder()
                .setId(customerEntity.id)
                .setName(customerEntity.name)
                .setEmail(customerEntity.email)
                .setBalance(customerEntity.balance)
                .build();
        CustomerResponse response = CustomerResponse.newBuilder().setCustomer(customer).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void updateCustomer(Customer request, StreamObserver<Empty> responseObserver) {
        CustomerEntity customerEntity = CustomerEntity.findById(request.getId());
        customerEntity.name = request.getName();
        customerEntity.email = request.getEmail();
        customerEntity.balance = request.getBalance();
        CustomerEntity.persistCustomer(customerEntity);

        responseObserver.onCompleted();
    }
}
