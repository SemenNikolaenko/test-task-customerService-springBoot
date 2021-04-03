package com.wevservice.web.repository;

import com.wevservice.web.model.Address;
import com.wevservice.web.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Customer getCustomerByFirstNameAndLastName(String firstName, String lastName);


}
