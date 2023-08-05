package com.example.esdras.demo.controller;

import com.example.esdras.demo.services.CustomerServiceImpl;
import com.example.esdras.demo.services.interfaces.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.core.Is.is;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl=new CustomerServiceImpl();


    @Test
    void listAllCustomers() throws Exception {
        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMvc.perform(get("/api/v1/customer") .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()",is(3)))
                .andExpect(jsonPath("$[0].id", is(customerServiceImpl.listCustomers().get(0).getId().toString())))
                .andExpect(jsonPath("$[0].name", is(customerServiceImpl.listCustomers().get(0).getName())));
    }

    @Test
    void getCustomerById() throws Exception {


        given(customerService.getCustomerId(customerServiceImpl.listCustomers().get(0).getId())).willReturn(customerServiceImpl.listCustomers().get(0));

        mockMvc.perform(get("/api/v1/customer/" + customerServiceImpl.listCustomers().get(0).getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(customerServiceImpl.listCustomers().get(0).getId().toString())))
                .andExpect(jsonPath("$.name", is(customerServiceImpl.listCustomers().get(0).getName())));

    }
}