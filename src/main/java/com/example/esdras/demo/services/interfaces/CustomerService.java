package com.example.esdras.demo.services.interfaces;


import com.example.esdras.demo.dto.CustomerDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDto> listCustomers();

    Optional<CustomerDto> getCustomerId(UUID id);

    CustomerDto saveCustomer(CustomerDto customer);

    CustomerDto updateCustomerById(UUID id, CustomerDto customer);

    CustomerDto deleteCustomerId(UUID id);

    CustomerDto patchCustomer(UUID id, CustomerDto customer);
}
