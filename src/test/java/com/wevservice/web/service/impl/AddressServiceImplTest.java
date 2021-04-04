package com.wevservice.web.service.impl;

import com.wevservice.web.exception.InvalidInputDataException;
import com.wevservice.web.model.Address;
import com.wevservice.web.params.AddressForUser;
import com.wevservice.web.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository repository;

    @InjectMocks
    private AddressServiceImpl service;

    private AddressForUser correctInputAddress;
    private AddressForUser anotherCorrectAddress;
    private AddressForUser incorrectInputAddress;
    Address fromDatabase;

    @BeforeEach
    void fillInInputAddressParams() {
        correctInputAddress = new AddressForUser();
        correctInputAddress.country = "Russia";
        correctInputAddress.region = "DVR";
        correctInputAddress.city = "Vladivostok";
        correctInputAddress.street = "Pushkina";
        correctInputAddress.house = "21";
        correctInputAddress.flat = "1";

        fromDatabase = new Address();
        fromDatabase.setId(1L);
        fromDatabase.setCountry("Russia");
        fromDatabase.setRegion("SZR");
        fromDatabase.setCity("Moscow");
        fromDatabase.setStreet("Lermontova");
        fromDatabase.setHouse("11");
        fromDatabase.setFlat("2");
        fromDatabase.setCreated(new GregorianCalendar(2021, 0, 24).getTime());
        fromDatabase.setModified(new GregorianCalendar(2021, 1, 1).getTime());

        anotherCorrectAddress = new AddressForUser();
        anotherCorrectAddress.country = "Russia";
        anotherCorrectAddress.region = "SZR";
        anotherCorrectAddress.city = "Moscow";
        anotherCorrectAddress.street = "Lermontova";
        anotherCorrectAddress.house = "11";
        anotherCorrectAddress.flat = "2";

        incorrectInputAddress = new AddressForUser();
        incorrectInputAddress.country = "";
        incorrectInputAddress.region = "SFO";
        incorrectInputAddress.city = " ";
        incorrectInputAddress.street = "Demyana";
        incorrectInputAddress.house = "23";
        incorrectInputAddress.flat = "112";
    }

    @Test
    public void shouldThrowExceptionIfInputDataIncomplete() {
        AddressForUser incompleteAddress = incorrectInputAddress;

        assertThrows(InvalidInputDataException.class, () -> service.save(incompleteAddress));
    }

    @Test
    public void ifCurrentAddressExistInDatabaseShouldReturnThisAddress() {
        Address expectedAddress = fromDatabase;
        AddressForUser input = anotherCorrectAddress;

        when(repository.exists(any())).thenReturn(true);
        when(repository.findOne(any())).thenReturn(Optional.of(expectedAddress));

        Address address = service.save(anotherCorrectAddress);

        assertEquals(expectedAddress, address);
        verify(repository, times(1)).findOne(any());
    }

    @Test
    public void ifAddressNotExistInDatabaseNotShouldFindItInDatabase() {
        Address expectedAddress = fromDatabase;
        AddressForUser input = anotherCorrectAddress;

        when(repository.exists(any())).thenReturn(false);
        when(repository.save(any())).thenReturn(expectedAddress);

        Address address = service.save(anotherCorrectAddress);

        assertEquals(expectedAddress, address);
        verify(repository, times(0)).findOne(any());
    }

    @Test
    public void ifExistAddressWithoutCustomerAddressShouldBeDelete(){
        List<Address> addressesFromDatabase = Arrays.asList(new Address(),new Address());

        when(repository.getAddressesWithoutCustomer()).thenReturn(addressesFromDatabase);

        service.deleteUnusedAddress();

        verify(repository,times(addressesFromDatabase.size())).deleteById(any());
    }
}