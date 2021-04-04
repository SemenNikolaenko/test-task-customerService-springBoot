package com.wevservice.web.params;

import lombok.EqualsAndHashCode;
//class allows transfer all information about customer and addresses into post's request body
@EqualsAndHashCode
public class InputParamsForCreatingCustomer {
   public NewCustomerParams customer;
   public AddressForUser actualAddress;
   public AddressForUser registryAddress;
}
