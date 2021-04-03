package com.wevservice.web.service.impl;

import com.wevservice.web.exception.InvalidInputDataException;
import com.wevservice.web.exception.NoDataFoundException;
import com.wevservice.web.model.Address;
import com.wevservice.web.model.Customer;
import com.wevservice.web.params.AddressForUser;
import com.wevservice.web.params.NewCustomerParams;
import com.wevservice.web.repository.AddressRepository;
import com.wevservice.web.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import com.wevservice.web.service.CustomerService;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    //в будующем возможно доделать связь для адреса чтобы можно было искать
    //текущего владельца адреса и текущего человека каоторый там зарегестрирован
    @Override
    public Customer create(NewCustomerParams customerParams, Address actualAddress, Address registryAddress) {
        Customer customer = null;
        if (checkCustomerInputParams(customerParams)) {
            customer = findInDatabaseOrCreate(customerParams);
        }
        customer.setActualAddress(actualAddress);
        customer.setRegisterAddress(registryAddress);

        return customerRepository.save(customer);
    }

    private Customer findInDatabaseOrCreate(NewCustomerParams params) {
        Customer customer = buildCustomer(params);
        Optional<Customer> fromDatabase = customerRepository.findOne(Example.of(customer));
        if (fromDatabase.isPresent()) {
            customer = fromDatabase.get();
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
        Customer customer = customerRepository.findById(customerId).get();
        customer.setActualAddress(address);
        customerRepository.save(customer);

        return customer;
    }

}
