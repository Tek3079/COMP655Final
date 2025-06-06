package com.example.customer;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.Blocking;
import jakarta.transaction.Transactional;

@GrpcService
public class CustomerServiceImpl extends CustomerServiceGrpc.CustomerServiceImplBase {

    @Override
    @Blocking
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
    @Blocking
    @Transactional
    public void updateCustomer(Customer request, StreamObserver<Empty> responseObserver) {
        try {
            CustomerEntity customerEntity = CustomerEntity.findById(request.getId());
            if (customerEntity == null) {
                responseObserver.onError(new Exception("Customer not found"));
                return;
            }

            customerEntity.name = request.getName();
            customerEntity.email = request.getEmail();
            customerEntity.balance = request.getBalance();
            customerEntity.persist();

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}