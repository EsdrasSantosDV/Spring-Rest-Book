package com.example.esdras.demo.controller;

import com.example.esdras.demo.dto.CustomerDto;
import com.example.esdras.demo.services.mock.CustomerServiceImpl;
import com.example.esdras.demo.services.interfaces.CustomerService;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.Is.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;
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
    ArgumentCaptor<CustomerDto> customerArgumentCaptor;


    @BeforeEach
    void setUp() {
        customerServiceImpl=new CustomerServiceImpl();
    }

    @Test
    void testPatchCustomer() throws Exception {
        CustomerDto customer = customerServiceImpl.listCustomers().get(0);

        //FAZER O MAP DO POJO PARA JSON
        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("name", "New Name");
        System.out.println(customerMap);

        mockMvc.perform(patch( CustomerController.CUSTOMER_PATH_ID, customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        verify(customerService).patchCustomer(uuidArgumentCaptor.capture(),
                customerArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(customer.getId());
        assertThat(customerArgumentCaptor.getValue().getName())
                .isEqualTo(customerMap.get("name"));
    }
    @Test
    void deleteCustomer() throws Exception{
        CustomerDto customer = customerServiceImpl.listCustomers().get(0);

        given(customerService.deleteCustomerId(any())).willReturn(true);

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH + '/' + customer.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomerId(uuidArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(customer.getId());
    }


    @Test
    void putCustomer() throws Exception {
        CustomerDto customer = customerServiceImpl.listCustomers().get(0);
        given(customerService.updateCustomerById(any(), any())).willReturn(Optional.of(CustomerDto.builder()
                .build()));
        mockMvc.perform(put(CustomerController.CUSTOMER_PATH + '/' + customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                        .andExpect(status().isNoContent());

        verify(customerService).updateCustomerById(any(UUID.class), any(CustomerDto.class));
    }


    @Test
    void createCustomer() throws Exception {
        CustomerDto createCustomer=customerServiceImpl.listCustomers().get(0);

        createCustomer.setId(null);
        createCustomer.setName("Esdras");

        given(customerService.saveCustomer((any(CustomerDto.class)))).willReturn(customerServiceImpl.listCustomers().get(1));

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

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH) .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()",is(3)))
                .andExpect(jsonPath("$[0].id", is(customerServiceImpl.listCustomers().get(0).getId().toString())))
                .andExpect(jsonPath("$[0].name", is(customerServiceImpl.listCustomers().get(0).getName())));
    }

    @Test
    void getCustomerByIdNotFound() throws Exception{
        given(customerService.getCustomerId(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH+'/'+UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



    @Test
    void getCustomerById() throws Exception {

        CustomerDto customerGet=customerServiceImpl.listCustomers().get(0);
        given(customerService.getCustomerId(customerServiceImpl.listCustomers().get(0).getId())).willReturn(Optional.of(customerGet));

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH+ '/' + customerServiceImpl.listCustomers().get(0).getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(customerServiceImpl.listCustomers().get(0).getId().toString())))
                .andExpect(jsonPath("$.name", is(customerServiceImpl.listCustomers().get(0).getName())));

    }
}