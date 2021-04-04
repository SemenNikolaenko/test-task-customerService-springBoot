package com.wevservice.web.service.impl;

import com.wevservice.web.exception.ExistingCustomerException;
import com.wevservice.web.exception.InvalidInputDataException;
import com.wevservice.web.exception.NoDataFoundException;
import com.wevservice.web.model.Address;
import com.wevservice.web.model.Customer;
import com.wevservice.web.params.NewCustomerParams;
import com.wevservice.web.repository.CustomerRepository;
import com.wevservice.web.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer create(NewCustomerParams customerParams, Address actualAddress, Address registryAddress) {
        Customer customer = null;
        if (checkCustomerInputParams(customerParams)) {
            //create new customer or throw new exception if customer already exist
            customer = checkInDatabaseOrCreate(customerParams);
        }
        //assign addresses for new customer
        customer.setActualAddress(actualAddress);
        customer.setRegisterAddress(registryAddress);

        return customerRepository.save(customer);
    }

    private Customer checkInDatabaseOrCreate(NewCustomerParams params) {
        //create new customer based on input parameters
        Customer customer = buildCustomer(params);
        //if customer is present in database throws exception
        if (customerRepository.exists(Example.of(customer))) {
            throw new ExistingCustomerException("This customer already exist");
        }
        return customer;
    }

    private Customer buildCustomer(NewCustomerParams params) {
        Customer customer = new Customer();
        customer.setFirstName(params.firstName);
        customer.setLastName(params.lastName);
        customer.setMiddleName(params.middleName);
        customer.setSex(params.sex.value);
        return customer;
    }


    private boolean checkCustomerInputParams(NewCustomerParams params) {
        //validate input parameters for customer
        if (!params.firstName.isBlank() && !params.lastName.isBlank()
                && !params.middleName.isBlank() && (params.sex != null && !params.sex.equals(""))
        ) return true;
        else throw new InvalidInputDataException("Customer's fields must be not empty");
    }


    @Override
    public Customer getByFirstNameAndLastName(String firstName, String lastName) {
        Customer customers = customerRepository.getCustomerByFirstNameAndLastName(firstName, lastName);
        if (customers != null) return customers;
        else throw new NoDataFoundException("No customer with present parameters");
    }

    @Override
    public Customer updateCustomerActualAddress(Address address, Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        Customer customer = null;
        //if customer exist do update else throw exception
        if (optionalCustomer.isPresent()) {
            customer = optionalCustomer.get();
        } else throw new NoDataFoundException("Nothing was found");
        customer.setActualAddress(address);

        return customerRepository.save(customer);
    }

}
