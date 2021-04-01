package service.impl;

import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CustomerRepository;
import service.CustomerService;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getById(Long id){
        Optional<Customer> byId = customerRepository.findById(id);
        if (byId.isPresent()) return byId.get();
        return null;
    }
    public boolean save(Customer customer){
        return false;
    }
    public boolean update( Customer customer){
        return false;
    }
}
