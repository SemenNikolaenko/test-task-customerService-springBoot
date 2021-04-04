package com.wevservice.web.params;

import lombok.EqualsAndHashCode;
//class allows transfer information about new customer
@EqualsAndHashCode
public class NewCustomerParams {
    public String firstName;
    public String lastName;
    public String middleName;
    public Sex sex;

}
