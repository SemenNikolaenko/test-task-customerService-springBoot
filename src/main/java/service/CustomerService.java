package service;

import model.Customer;

public interface CustomerService {
    public Customer getById(Long id);
    public boolean save(Customer customer);
    public boolean update(Customer customer);
}
