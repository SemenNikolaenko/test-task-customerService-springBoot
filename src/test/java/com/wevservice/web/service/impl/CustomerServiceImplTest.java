package com.wevservice.web.service.impl;

import com.wevservice.web.exception.ExistingCustomerException;
import com.wevservice.web.exception.InvalidInputDataException;
import com.wevservice.web.exception.NoDataFoundException;
import com.wevservice.web.model.Address;
import com.wevservice.web.model.Customer;
import com.wevservice.web.params.NewCustomerParams;
import com.wevservice.web.params.Sex;
import com.wevservice.web.repository.AddressRepository;
import com.wevservice.web.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.GregorianCalendar;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private static Address actual;
    private static Address registry;
    private static Customer customer;
    private static NewCustomerParams correctParams;
    private static NewCustomerParams incorrectParams;

    @BeforeAll
    public static void init() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Musha");
        customer.setLastName("Ezhev");
        customer.setMiddleName("Alekseevich");
        customer.setSex("male");
        customer.setActualAddress(actual);
        customer.setRegisterAddress(registry);

        correctParams = new NewCustomerParams();
        correctParams.firstName = "Musha";
        correctParams.lastName = "Ezhev";
        correctParams.middleName = "Alekseevich";
        correctParams.sex = Sex.M;

        incorrectParams = new NewCustomerParams();
        incorrectParams.firstName = "";
        incorrectParams.lastName = " ";
        incorrectParams.middleName = "Alekseevich";
        incorrectParams.sex = Sex.M;

        actual = new Address();
        actual.setId(1L);
        actual.setCountry("Russia");
        actual.setRegion("SZR");
        actual.setCity("Moscow");
        actual.setStreet("Lermontova");
        actual.setHouse("11");
        actual.setFlat("2");
        actual.setCreated(new GregorianCalendar(2021, 0, 24).getTime());
        actual.setModified(new GregorianCalendar(2021, 1, 1).getTime());

        registry = new Address();
        registry.setId(2L);
        registry.setCountry("Russia");
        registry.setRegion("Ural");
        registry.setCity("Chel");
        registry.setStreet("Lermontova");
        registry.setHouse("121");
        registry.setFlat("23");
        registry.setCreated(new GregorianCalendar(2021, 2, 12).getTime());
        registry.setModified(new GregorianCalendar(2021, 3, 2).getTime());
    }


    @Test
    public void shouldThrowExceptionIfInputDataIncomplete() {

        assertThrows(InvalidInputDataException.class, () -> customerService.create(incorrectParams, actual, registry));
        verify(customerRepository, times(0)).exists(any());

    }

    @Test
    public void ifCustomerWithSameParametersAlreadyExistShouldThrowException() {
        when(customerRepository.exists(any())).thenReturn(true);

        assertThrows(ExistingCustomerException.class, () -> customerService.create(correctParams, actual, registry));
        verify(customerRepository, times(1)).exists(any());

    }

    @Test
    public void ifCustomerNotExistShouldCreateAndReturnThisCustomer() {
        when(customerRepository.save(any())).thenReturn(customer);
        when(customerRepository.exists(any())).thenReturn(false);


        assertEquals(customerService.create(correctParams, actual, registry), customer);
        verify(customerRepository, times(1)).exists(any());
        verify(customerRepository, times(1)).save(any());
    }

    @Test
    public void shouldReturnCustomerByFirstNameAndLastName() {
        String firstName = "Musha";
        String lastName = "Ezhev";
        when(customerRepository.getCustomerByFirstNameAndLastName(firstName, lastName)).thenReturn(customer);

        assertEquals(customer, customerService.getByFirstNameAndLastName(firstName, lastName));
    }

    @Test
    public void shouldThrowExceptionIfDataNotFound() {
        String firstName = "Mush";
        String lastName = "Ezhev";
        when(customerRepository.getCustomerByFirstNameAndLastName(firstName, lastName)).thenReturn(null);

        assertThrows(NoDataFoundException.class,()-> customerService.getByFirstNameAndLastName(firstName,lastName));
    }

    @Test
    public void shouldUpdateCustomer(){
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        when(customerRepository.save(any())).thenReturn(customer);

        assertEquals(customer,customerService.updateCustomerActualAddress(actual,1L));
    }


}