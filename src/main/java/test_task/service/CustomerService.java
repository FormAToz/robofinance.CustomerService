package test_task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test_task.api.request.CustomerRequest;
import test_task.api.request.FirstAndLastNameRequest;
import test_task.model.Address;
import test_task.model.Customer;
import test_task.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressService addressService;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getByFullNameAndSexAndRegisteredAddress(CustomerRequest request) {
        String error = String.format(
                "Клиент %s %s %s, с адресом регистрации(%s) не существует",
                request.getLastName(),
                request.getFirstName(),
                request.getMiddleName(),
                request.getRegisteredAddress());
        Address registeredAddress = addressService.getFromAddressRequest(request.getRegisteredAddress());

        return customerRepository
                .findByFirstNameIgnoreCaseAndMiddleNameIgnoreCaseAndLastNameIgnoreCaseAndSexAndRegisteredAddress(
                        request.getFirstName(),
                        request.getMiddleName(),
                        request.getLastName(),
                        request.getSex(),
                        registeredAddress)
                .orElseThrow(() -> new IllegalArgumentException(error));
    }

    public Customer addCustomer(CustomerRequest request) {
        Address registeredAddress = addressService.saveNewFromAddressRequest(request.getRegisteredAddress());
        Address actualAddress = addressService.saveNewFromAddressRequest(request.getActualAddress());

        Customer customer = Customer.builder()
                .registeredAddress(registeredAddress)
                .actualAddress(actualAddress)
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .sex(request.getSex())
                .build();

        return customerRepository.save(customer);
    }

    /**
     * В данной реализации метод изменяет только фактический адрес клиента
     */
    public Customer updateCustomer(CustomerRequest request) {
        Customer currentCustomer = getByFullNameAndSexAndRegisteredAddress(request);
        Address newActualAddress = addressService.updateFromAddressRequest(request.getActualAddress());

        currentCustomer.setActualAddress(newActualAddress);
        return customerRepository.save(currentCustomer);
    }

    public Customer getByFirstNameAndLastName(FirstAndLastNameRequest request) {
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String error = String.format("Клиента с именем %s %s не существует", firstName, lastName);

        return customerRepository.findByFirstNameAndLastNameIgnoreCase(firstName, lastName)
                .orElseThrow(() -> new IllegalArgumentException(error));
    }
}