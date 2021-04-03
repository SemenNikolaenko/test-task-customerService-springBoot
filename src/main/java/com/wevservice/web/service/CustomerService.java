package com.wevservice.web.service;

import com.wevservice.web.model.Address;
import com.wevservice.web.model.Customer;
import com.wevservice.web.params.AddressForUser;
import com.wevservice.web.params.NewCustomerParams;

import java.util.List;

public interface CustomerService {
    public Customer create(NewCustomerParams params, Address actualAddress, Address registryAddress);
    public Customer getByFirstNameAndLastName(String firstName,String lastName);
    public Customer updateCustomerActualAddress(Address address, Long customerId);

}
