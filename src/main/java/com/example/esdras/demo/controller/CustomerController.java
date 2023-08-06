package com.example.esdras.demo.controller;

import com.example.esdras.demo.model.Book;
import com.example.esdras.demo.model.Customer;
import com.example.esdras.demo.services.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService customerService;
    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";


    @GetMapping(CUSTOMER_PATH)
    public List<Customer> listAllCustomers(){
        return customerService.listCustomers();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public Customer getCustomerById(@PathVariable("customerId") UUID id){
        return customerService.getCustomerId(id);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public Customer deleteCustomerById(@PathVariable("customerId") UUID customerId){
        return customerService.deleteCustomerId(customerId);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity createBook(@RequestBody Customer customer){
        Customer customerCreated= customerService.saveCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH+'/' + customerCreated.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomerByID(@PathVariable("customerId") UUID customerId,
                                             @RequestBody Customer customer){
        Customer customerUpdated = customerService.updateCustomerById(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity patchCustomerById(@PathVariable("customerId") UUID customerId,
                                            @RequestBody Customer customer){
        Customer patchedCustomer = customerService.patchCustomer(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
