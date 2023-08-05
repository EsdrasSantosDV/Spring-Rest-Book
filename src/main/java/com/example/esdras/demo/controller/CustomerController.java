package com.example.esdras.demo.controller;

import com.example.esdras.demo.model.Customer;
import com.example.esdras.demo.services.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService customerService;



    @DeleteMapping("{customerId}")
    public Customer deleteCustomerById(@PathVariable("customerId") UUID customerId){
        return customerService.deleteCustomerId(customerId);
    }

    @PutMapping("{customerId}")
    public Customer updateCustomerByID(@PathVariable("customerId") UUID customerId,
                                             @RequestBody Customer customer){
        return customerService.updateCustomerById(customerId, customer);
    }

    @PostMapping
    public Customer handlePost(@RequestBody Customer customer){
        return customerService.saveCustomer(customer);
    }

    @GetMapping()
    public List<Customer> listAllCustomers(){
        return customerService.listCustomer();
    }

    @GetMapping("{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") UUID id){
        return customerService.getCustomerId(id);
    }

    @PatchMapping("{customerId}")
    public Customer patchCustomerById(@PathVariable("customerId") UUID customerId,
                                            @RequestBody Customer customer){

        return customerService.patchCustomer(customerId, customer);
    }


}
