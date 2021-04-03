package com.wevservice.web.controller;

import com.wevservice.web.model.Address;
import com.wevservice.web.model.Customer;
import com.wevservice.web.params.AddressForUser;
import com.wevservice.web.params.InputParamsForCreatingCustomer;
import com.wevservice.web.service.AddressService;
import com.wevservice.web.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final AddressService addressService;

    @Autowired
    public CustomerController(CustomerService customerService, AddressService addressService) {
        this.customerService = customerService;
        this.addressService = addressService;
    }

    @PostMapping()
    public ResponseEntity<Customer> createNewCustomer(
            @RequestBody InputParamsForCreatingCustomer params
    ) {
        Address actualAddress = addressService.save(params.actualAddress);
        Address registryAddress = addressService.save(params.registryAddress);
        Customer customer = customerService.create(params.customer, actualAddress, registryAddress);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/customer")
    public ResponseEntity<Customer> getCustomersByLastAndFirstName(@RequestParam String firstName,
                                                                         @RequestParam String lastName) {
        Customer customers = customerService.getByFirstNameAndLastName(firstName, lastName);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateActualAddress(@RequestBody AddressForUser address,
                                                        @PathVariable("id") Long customerId){
        Address presentActualAddress = addressService.save(address);
        Customer updatedCustomer = customerService.updateCustomerActualAddress(presentActualAddress, customerId);
        addressService.deleteUnusedAddress();
        return new ResponseEntity<>(updatedCustomer,HttpStatus.OK);
    }


}
