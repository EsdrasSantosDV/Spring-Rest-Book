package com.example.esdras.demo.services.interfaces;


import com.example.esdras.demo.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> listCustomer();

    Customer getCustomerId(UUID id);

    Customer saveCustomer(Customer customer);

    Customer updateCustomerById(UUID id, Customer customer);

    Customer deleteCustomerId(UUID id);

    Customer patchCustomer(UUID id, Customer customer);
}
