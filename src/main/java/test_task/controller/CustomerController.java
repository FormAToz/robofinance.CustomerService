package test_task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import test_task.api.request.AddressRequest;
import test_task.api.request.CustomerRequest;
import test_task.api.request.FirstAndLastNameRequest;

@Controller
public class CustomerController {

    @PostMapping("/add")
    public String addCustomer(CustomerRequest request) {
        return null;
    }

    @PutMapping("/{id}")
    public String updateCustomer(CustomerRequest request) {
        return null;
    }

    @GetMapping("/{id}")
    public String getCustomerById(@PathVariable long id) {
        return null;
    }

    @GetMapping("/address")
    public String getCustomerByActualAddress(AddressRequest request) {
        return null;
    }

    @GetMapping("/name")
    public String getCustomerByFirstNameAndLastName(FirstAndLastNameRequest request) {
        return null;
    }
}