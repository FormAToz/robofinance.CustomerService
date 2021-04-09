package test_task.service;

import org.springframework.stereotype.Service;
import test_task.api.request.AddressRequest;
import test_task.api.request.CustomerRequest;
import test_task.api.request.FirstAndLastNameRequest;
import test_task.model.Customer;

import java.util.List;

@Service
public class CustomerService {
    public boolean addCustomer(CustomerRequest request) {
        return false;
    }

    public boolean updateCustomer(CustomerRequest request) {
        return false;
    }

    public List<Customer> getAllCustomers() {
        return null;
    }

    public Customer getById(long id) {
        return null;
    }

    public Customer getByActualAddress(AddressRequest request) {
        return null;
    }

    public Customer getByFirstNameAndLastName(FirstAndLastNameRequest request) {
        return null;
    }
}