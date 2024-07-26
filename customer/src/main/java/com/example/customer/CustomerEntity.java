package com.example.customer;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Random;

@Entity
@Table(name = "customer")
public class CustomerEntity extends PanacheEntity {

    @NotBlank
    public String name;

    @NotBlank
    public String email;

    @Min(0)
    public Double balance;

    public static CustomerEntity findRandomCustomer() {
        int startIndex = new Random().nextInt((int) count());
        return CustomerEntity.findAll().page(startIndex, 1).firstResult();
    }

    public static CustomerEntity persistCustomer(@Valid CustomerEntity customerEntity) {
        CustomerEntity.persist(customerEntity);
        return customerEntity;
    }
}