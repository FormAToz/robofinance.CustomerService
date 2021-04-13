package test_task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test_task.api.request.CustomerRequest;
import test_task.api.request.FirstAndLastNameRequest;
import test_task.model.Customer;
import test_task.service.CustomerService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@RequestBody CustomerRequest request) {
        return new ResponseEntity<>(customerService.addCustomer(request), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody CustomerRequest request) {
        return new ResponseEntity<>(customerService.updateCustomer(request), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Customer> searchCustomer(@RequestBody FirstAndLastNameRequest request) {
        return new ResponseEntity<>(customerService.getByFirstNameAndLastName(request), HttpStatus.OK);
    }
}