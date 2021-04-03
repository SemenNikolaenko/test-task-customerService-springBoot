package com.wevservice.web.service;

import com.wevservice.web.model.Address;
import com.wevservice.web.params.AddressForUser;

public interface AddressService {
    public Address save(AddressForUser address);
    public void deleteUnusedAddress();
}
