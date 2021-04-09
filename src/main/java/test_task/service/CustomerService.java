package test_task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test_task.api.request.CustomerRequest;
import test_task.api.request.FirstAndLastNameRequest;
import test_task.model.Address;
import test_task.model.Customer;
import test_task.model.enums.Sex;
import test_task.repository.CustomerRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressService addressService;

    @PostConstruct
    private void generateTestCustomer() {
        Address regAddress = Address.builder()
                .country("Россия")
                .region("Московская область")
                .city("Балашиха")
                .street("Агрогородок")
                .house("7")
                .flat("77")
                .created(LocalDateTime.now())
                .modified(null)
                .build();

        Address actAddress = Address.builder()
                .country("Россия")
                .region("Московская область")
                .city("Балашиха")
                .street("Агрогородок")
                .house("7")
                .flat("77")
                .created(LocalDateTime.now())
                .modified(null)
                .build();

        addressService.addAddress(actAddress);
        addressService.addAddress(regAddress);

        Customer customer = Customer.builder()
                .registeredAddress(regAddress)
                .actualAddress(actAddress)
                .firstName("Андрей")
                .middleName("Сергеевич")
                .lastName("Данилов")
                .sex(Sex.MALE)
                .build();

        customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getByFullNameAndSexAndRegisteredAddress(CustomerRequest request) {
        String error = String.format(
                "Клиент %s %s %s не существует",
                request.getLastName(),
                request.getFirstName(),
                request.getMiddleName());
        Address registeredAddress = addressService.getFromAddressRequest(request.getRegisteredAddress());

        return customerRepository
                .findByFirstNameAndMiddleNameAndLastNameAndSexAndRegisteredAddress(
                        request.getFirstName(),
                        request.getMiddleName(),
                        request.getLastName(),
                        request.getSex(),
                        registeredAddress)
                .orElseThrow(() -> new IllegalArgumentException(error));
    }

    public Customer addCustomer(CustomerRequest request) {
        Address registeredAddress = addressService.saveFromAddressRequest(request.getRegisteredAddress());
        Address actualAddress = addressService.saveFromAddressRequest(request.getActualAddress());

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
        Address newActualAddress = addressService.getFromAddressRequest(request.getActualAddress());

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