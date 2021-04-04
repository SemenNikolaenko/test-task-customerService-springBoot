package com.wevservice.web.params;

import lombok.EqualsAndHashCode;
//special class for transfer data about address
@EqualsAndHashCode
public class AddressForUser {
    public String country;
    public String region;
    public String city;
    public String street;
    public String house;
    public String flat;
}
