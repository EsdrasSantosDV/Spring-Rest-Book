package com.example.esdras.demo.services;

import com.example.esdras.demo.model.Customer;
import com.example.esdras.demo.services.interfaces.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 2")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 3")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerMap = new HashMap<>();
        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }
    @Override
    public List<Customer> listCustomer() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerId(UUID id) {
        return customerMap.get(id);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        Customer savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .updateDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .name(customer.getName())
                .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }

    @Override
    public Customer updateCustomerById(UUID id, Customer customer) {
        Customer existing = customerMap.get(id);
        existing.setName(customer.getName());

        return existing;
    }

    @Override
    public Customer deleteCustomerId(UUID id) {
        Customer customer = customerMap.get(id);
        customerMap.remove(id);
        return customer;
    }

    @Override
    public Customer patchCustomer(UUID id, Customer customer) {
        Customer existing = customerMap.get(id);

        if (StringUtils.hasText(customer.getName())) {
            existing.setName(customer.getName());
        }
        return existing;
    }
}
