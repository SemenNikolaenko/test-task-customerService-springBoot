package com.wevservice.web.service.impl;

import com.wevservice.web.exception.InvalidInputDataException;
import com.wevservice.web.model.Address;
import com.wevservice.web.params.AddressForUser;
import com.wevservice.web.repository.AddressRepository;
import com.wevservice.web.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address save(AddressForUser actualAddress) {
        Address address = null;
        if (checkCustomerAddress(actualAddress)) {
            //check address in database
            //if present retrieves address from database with existing id
            //if  doesn't present it will be created without id
            address = findInDatabaseOrCreate(actualAddress);
        }
        if (address.getId()==null) return  addressRepository.save(address);
        else return address;
    }

    @Override
    public void deleteUnusedAddress() {
        //get all address without customers
        List<Address> addressWithoutCustomers = addressRepository.getAddressesWithoutCustomer();
        if (addressWithoutCustomers.size() > 0) {
            //iterate by list and removing element from database
            for (Address a : addressWithoutCustomers) {
                addressRepository.deleteById(a.getId());
            }
        }
    }

    private Address buildNewAddressForCustomer(AddressForUser params) {
        Address address = new Address();
        address.setCountry(params.country);
        address.setRegion(params.region);
        address.setCity(params.city);
        address.setStreet(params.street);
        address.setHouse(params.house);
        address.setFlat(params.flat);
        return address;
    }


    private Address findInDatabaseOrCreate(AddressForUser address) {

        Address newAddress = buildNewAddressForCustomer(address);
        //if database already exist present address
        //this address will be retrieved with it's id
        if (addressRepository.exists(Example.of(newAddress))) {
            newAddress = addressRepository.findOne(Example.of(newAddress)).get();
        }
        return newAddress;

    }

    private boolean checkCustomerAddress(AddressForUser params) {
        //validate input parameters for address
        if (params.country.isBlank() || params.region.isBlank() || params.city.isBlank()
                || params.street.isBlank() || params.house.isBlank() || params.house.isBlank()
        ) throw new InvalidInputDataException("Address's fields must be not empty");
        else return true;
    }
}
