package com.wevservice.web.repository;

import com.wevservice.web.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Customer getCustomerByFirstNameAndLastName(String firstName, String lastName);


}
