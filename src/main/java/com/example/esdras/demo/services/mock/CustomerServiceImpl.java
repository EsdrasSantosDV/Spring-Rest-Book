package com.example.esdras.demo.services.mock;

import com.example.esdras.demo.dto.CustomerDto;
import com.example.esdras.demo.services.interfaces.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, CustomerDto> customerMap;

    public CustomerServiceImpl() {
        CustomerDto customer1 = CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDto customer2 = CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Customer 2")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDto customer3 = CustomerDto.builder()
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
    public List<CustomerDto> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDto> getCustomerId(UUID id) {
        return Optional.of(customerMap.get(id));
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customer) {
        CustomerDto savedCustomer = CustomerDto.builder()
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
    public Optional<CustomerDto> updateCustomerById(UUID id, CustomerDto customer) {
        CustomerDto existing = customerMap.get(id);
        existing.setName(customer.getName());

        return Optional.of(existing);
    }

    @Override
    public Boolean deleteCustomerId(UUID id) {
        customerMap.remove(id);
        return true;
    }

    @Override
    public Optional<CustomerDto> patchCustomer(UUID id, CustomerDto customer) {
        CustomerDto existing = customerMap.get(id);

        if (StringUtils.hasText(customer.getName())) {
            existing.setName(customer.getName());
        }
        return Optional.of(existing);
    }
}
