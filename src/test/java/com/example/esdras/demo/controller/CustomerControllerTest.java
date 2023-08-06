package com.example.esdras.demo.controller;

import com.example.esdras.demo.model.Book;
import com.example.esdras.demo.model.Customer;
import com.example.esdras.demo.services.CustomerServiceImpl;
import com.example.esdras.demo.services.interfaces.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.core.Is.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @Autowired
    ObjectMapper  objectMapper;

    CustomerServiceImpl customerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;


    @BeforeEach
    void setUp() {
        customerServiceImpl=new CustomerServiceImpl();
    }



    @Test
    void putCustomer() throws Exception {
        Customer customer = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH + '/' + customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                        .andExpect(status().isNoContent());

        verify(customerService).updateCustomerById(any(UUID.class), any(Customer.class));
    }


    @Test
    void createCustomer() throws Exception {
        Customer createCustomer=customerServiceImpl.listCustomers().get(0);

        createCustomer.setId(null);
        createCustomer.setName("Esdras");

        given(customerService.saveCustomer((any(Customer.class)))).willReturn(customerServiceImpl.listCustomers().get(1));

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCustomer)))
                        .andExpect(status().isCreated())
                         .andExpect(header().string("Location", CustomerController.CUSTOMER_PATH+'/' + customerServiceImpl.listCustomers().get(1).getId().toString()))
                         .andExpect(header().exists("Location"));

    }


    @Test
    void listAllCustomers() throws Exception {
        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMvc.perform(get(BookController.BOOK_PATH) .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()",is(3)))
                .andExpect(jsonPath("$[0].id", is(customerServiceImpl.listCustomers().get(0).getId().toString())))
                .andExpect(jsonPath("$[0].name", is(customerServiceImpl.listCustomers().get(0).getName())));
    }

    @Test
    void getCustomerById() throws Exception {


        given(customerService.getCustomerId(customerServiceImpl.listCustomers().get(0).getId())).willReturn(customerServiceImpl.listCustomers().get(0));

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH+ '/' + customerServiceImpl.listCustomers().get(0).getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(customerServiceImpl.listCustomers().get(0).getId().toString())))
                .andExpect(jsonPath("$.name", is(customerServiceImpl.listCustomers().get(0).getName())));

    }
}