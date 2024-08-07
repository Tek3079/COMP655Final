package com.example.customer;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;

@Tag(name = "Customer API")
@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @GET
    @Path("/customers")
    @Transactional(Transactional.TxType.SUPPORTS)
    @Tag(name = "getAllCustomer", description = "getAllCustomer")
    public Response getAllCustomer() {
        return Response.ok(CustomerEntity.findAll().list()).build();
    }

    @GET
    @Path("/customer/{id}")
    @Transactional(Transactional.TxType.SUPPORTS)
    @Tag(name = "getCustomerById", description = "getCustomerById")
    public Response getCustomerById(@PathParam("id") Long id) {
        CustomerEntity customer = CustomerEntity.findById(id);
        if (customer == null) {
            return Response.noContent().build();
        }
        return Response.ok(customer).build();
    }

    @GET
    @Path("/customer/random")
    @Transactional(Transactional.TxType.SUPPORTS)
    @Tag(name = "getRandomCustomer", description = "getRandomCustomer")
    public Response getRandomCustomer() {
        return Response.ok(CustomerEntity.findRandomCustomer()).build();
    }

    @PUT
    @Path("/customer/{id}")
    @Transactional(Transactional.TxType.REQUIRED)
    @Tag(name = "updateCustomer", description = "updateCustomer")
    public Response updateCustomer(@PathParam("id") Long id, @Valid CustomerEntity customerEntity) {
        CustomerEntity c = CustomerEntity.findById(id);
        if (c == null) {
            return Response.noContent().build();
        }
        c.name = customerEntity.name;
        c.email = customerEntity.email;
        c.balance = customerEntity.balance;
        return Response.ok(CustomerEntity.persistCustomer(c)).build();
    }

    @POST
    @Path("/customer")
    @Transactional(Transactional.TxType.REQUIRED)
    @Tag(name = "createCustomer", description = "createCustomer")
    public Response createCustomer(@Valid CustomerEntity customerEntity) {
        CustomerEntity c = new CustomerEntity();
        c.name = customerEntity.name;
        c.email = customerEntity.email;
        c.balance = customerEntity.balance;
        CustomerEntity.persistCustomer(c);
        return Response.created(URI.create("/customer/" + c.id)).build();
    }

    @DELETE
    @Path("/customer/{id}")
    @Transactional(Transactional.TxType.REQUIRED)
    @Tag(name = "deleteCustomer", description = "deleteCustomer")
    public Response deleteCustomer(@PathParam("id") Long id) {
        CustomerEntity.deleteById(id);
        return Response.ok().build();
    }

}