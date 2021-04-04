package com.wevservice.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wevservice.web.exception.InvalidInputDataException;
import com.wevservice.web.model.Address;
import com.wevservice.web.model.Customer;
import com.wevservice.web.params.AddressForUser;
import com.wevservice.web.params.InputParamsForCreatingCustomer;
import com.wevservice.web.params.NewCustomerParams;
import com.wevservice.web.params.Sex;
import com.wevservice.web.service.AddressService;
import com.wevservice.web.service.CustomerService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.GregorianCalendar;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @MockBean
    CustomerService customerService;
    @MockBean
    AddressService addressService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    private static Address actual;
    private static Address registry;
    private static Customer customer;

    @BeforeAll
    public static void init() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Musha");
        customer.setLastName("Ezhev");
        customer.setMiddleName("Alekseevich");
        customer.setSex("male");
        customer.setActualAddress(actual);
        customer.setRegisterAddress(registry);

        actual = new Address();
        actual.setId(1L);
        actual.setCountry("Russia");
        actual.setRegion("SZR");
        actual.setCity("Moscow");
        actual.setStreet("Lermontova");
        actual.setHouse("11");
        actual.setFlat("2");
        actual.setCreated(new GregorianCalendar(2021, 0, 24).getTime());
        actual.setModified(new GregorianCalendar(2021, 1, 1).getTime());

        registry = new Address();
        registry.setId(2L);
        registry.setCountry("Russia");
        registry.setRegion("Ural");
        registry.setCity("Chel");
        registry.setStreet("Lermontova");
        registry.setHouse("121");
        registry.setFlat("23");
        registry.setCreated(new GregorianCalendar(2021, 2, 12).getTime());
        registry.setModified(new GregorianCalendar(2021, 3, 2).getTime());
    }

    @Test
    public void shouldReturnCreatedCustomer() throws Exception {
        Address someAddress = actual;
        NewCustomerParams customerParams = new NewCustomerParams();
        customerParams.middleName = "a";
        customerParams.firstName = "a";
        customerParams.sex = Sex.M;
        customerParams.lastName = "a";
        AddressForUser address1 = new AddressForUser();
        AddressForUser address2 = new AddressForUser();
        InputParamsForCreatingCustomer params = new InputParamsForCreatingCustomer();
        params.customer = customerParams;
        params.actualAddress = address1;
        params.registryAddress = address2;


        when(addressService.save(any(AddressForUser.class))).thenReturn(actual);
        when(customerService.create(customerParams, actual, actual)).thenReturn(customer);


        mockMvc.perform(post("/customers/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customer)));

    }

    @Test
    public void shouldReturnCustomerByFirstNameAndLastName() throws Exception {
        when(customerService.getByFirstNameAndLastName(anyString(), anyString())).thenReturn(customer);

        mockMvc.perform(get("/customers")
                .queryParam("firstName", String.valueOf("firstName"))
                .queryParam("lastName", String.valueOf("lastName")))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customer)));
    }

    @Test
    public void shouldReturnUpdateCustomer() throws Exception {
        AddressForUser address = new AddressForUser();
        Long customerId = 1L;
        when(addressService.save(address)).thenReturn(actual);
        when(customerService.updateCustomerActualAddress(actual, customerId)).thenReturn(customer);

        mockMvc.perform(put("/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(address))
                .queryParam("customerId", String.valueOf(customerId))
        )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customer)));
    }


}