package com.example.esdras.demo.services.jpa;

import com.example.esdras.demo.dto.CustomerDto;
import com.example.esdras.demo.mappers.CustomerMapper;
import com.example.esdras.demo.repositories.CustomerRepository;
import com.example.esdras.demo.services.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Primary
public class CustomerServiceJpa implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    //CONVERTER O RETORNO NO TIPO DE RESPOSTA ESPECIFICO
    @Override
    public List<CustomerDto> listCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::customerEntityToCustomerDto).collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDto> getCustomerId(UUID id) {
        //PQ ELE PODE RETORNAR NULL OU A ENTITY MESMO
        //AI JA USA O MAPPER PRA REALIZAR A CONVERSAO
        return Optional.ofNullable(customerMapper.customerEntityToCustomerDto(customerRepository.findById(id).orElse(null)));
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customer) {
        return customerMapper.customerEntityToCustomerDto(customerRepository.save(customerMapper.customerDtoToCustomerEntity(customer)));
    }

    @Override
    public CustomerDto updateCustomerById(UUID id, CustomerDto customer) {
        return null;
    }

    @Override
    public Boolean deleteCustomerId(UUID id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public CustomerDto patchCustomer(UUID id, CustomerDto customer) {
        return null;
    }
}
