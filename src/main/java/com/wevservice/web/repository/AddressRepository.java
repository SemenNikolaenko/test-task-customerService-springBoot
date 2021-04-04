package com.wevservice.web.repository;

import com.wevservice.web.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {

    //method allows retrieve information about addresses without customers
    @Query("select a from Address a left join\n" +
            "    Customer c on (a.id = c.actualAddress.id or  a.id=c.registerAddress.id) where c.id IS NULL ")
    public List<Address> getAddressesWithoutCustomer();
}
