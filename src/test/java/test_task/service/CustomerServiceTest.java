package test_task.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import test_task.api.request.AddressRequest;
import test_task.api.request.CustomerRequest;
import test_task.api.request.FirstAndLastNameRequest;
import test_task.model.Address;
import test_task.model.Customer;
import test_task.model.enums.Sex;
import test_task.repository.AddressRepository;
import test_task.repository.CustomerRepository;

import javax.annotation.Resource;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceTest {
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ Entities in DB ++++++++++++++++++++++++++++++++++++++++++

    private static Address registeredAddress = Address.builder()
            .country("Россия")
            .region("Московская область")
            .city("Балашиха")
            .street("Агрогородок")
            .house("7")
            .flat("77")
            .build();
    private static Address actualAddress = Address.builder()
            .country("Россия")
            .region("Москва")
            .city("Москва")
            .street("Новая")
            .house("11")
            .flat("123")
            .build();
    private static Customer customer1 = Customer.builder()
            .firstName("Андрей")
            .middleName("Сергеевич")
            .lastName("Данилов")
            .sex(Sex.MALE)
            .registeredAddress(registeredAddress)
            .actualAddress(actualAddress)
            .build();
    private static Customer customer2 = Customer.builder()
            .firstName("Дарья")
            .middleName("Алексеевна")
            .lastName("Балобанова")
            .sex(Sex.FEMALE)
            .registeredAddress(registeredAddress)
            .actualAddress(actualAddress)
            .build();

    //++++++++++++++++++++++++++++++++++++++++++++ Request Entities +++++++++++++++++++++++++++++++++++++++++++++++++++

    private static AddressRequest registeredAddressRequest_1 = AddressRequest.builder()
            .country("Россия")
            .region("Московская область")
            .city("Балашиха")
            .street("Агрогородок")
            .house("7")
            .flat("77")
            .build();
    private static AddressRequest actualAddressRequest_1 = AddressRequest.builder()
            .country("Россия")
            .region("Москва")
            .city("Москва")
            .street("Новая")
            .house("11")
            .flat("123")
            .build();
    private static CustomerRequest customerRequest_1 = CustomerRequest.builder()
            .firstName("Дарья")
            .middleName("Алексеевна")
            .lastName("Балобанова")
            .sex(Sex.FEMALE)
            .registeredAddress(registeredAddressRequest_1)
            .actualAddress(actualAddressRequest_1)
            .build();

    private static AddressRequest registeredAddressRequest_2 = AddressRequest.builder()
            .country("Россия")
            .region("Владимирская область")
            .city("Владимир")
            .street("Ленина")
            .house("12")
            .flat("45")
            .build();
    private static CustomerRequest customerRequest_2 = CustomerRequest.builder()
            .firstName("Елена")
            .middleName("Петровна")
            .lastName("Васильева")
            .sex(Sex.FEMALE)
            .registeredAddress(registeredAddressRequest_2)
            .actualAddress(registeredAddressRequest_2)
            .build();

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Autowired
    private CustomerService customerService;
    @Resource
    private CustomerRepository customerRepository;
    @Resource
    private AddressRepository addressRepository;

    @Before
    public void setUp() {
        addressRepository.saveAll(Set.of(registeredAddress, actualAddress));
        customerRepository.saveAll(Set.of(customer1, customer2));
    }

    @Test
    public void testContextLoad() {
        assertThat(customerService).isNotNull();
        assertThat(addressRepository).isNotNull();
    }

    /**
     * Получение всех клиентов
     */
    @Test
    public void testGetAllCustomers() {
        assertEquals(customerService.getAllCustomers().size(), 2);
    }

    /**
     * Получение клиента по имени, полу и регистрации
     */
    @Test
    public void testGetByFullNameAndSexAndRegisteredAddress() {
        assertEquals(customerService.getByFullNameAndSexAndRegisteredAddress(customerRequest_1).getFirstName(), "Дарья");
        assertEquals(customerService.getByFullNameAndSexAndRegisteredAddress(customerRequest_1).getMiddleName(), "Алексеевна");
        assertEquals(customerService.getByFullNameAndSexAndRegisteredAddress(customerRequest_1).getLastName(), "Балобанова");
        assertEquals(customerService.getByFullNameAndSexAndRegisteredAddress(customerRequest_1).getSex(), Sex.FEMALE);
        assertEquals(customerService
                .getByFullNameAndSexAndRegisteredAddress(customerRequest_1)
                .getRegisteredAddress()
                .getStreet(), "Агрогородок");
    }

    /**
     * Создание клиента
     */
    @Test
    public void testAddCustomer() {
        Customer expectedCustomer = customerService.addCustomer(customerRequest_2);

        assertEquals(expectedCustomer.getFirstName(), "Елена");
        assertEquals(expectedCustomer.getMiddleName(), "Петровна");
        assertEquals(expectedCustomer.getLastName(), "Васильева");
        assertEquals(expectedCustomer.getSex(), Sex.FEMALE);

        customerRepository.delete(expectedCustomer);
    }

    /**
     * Изменения данных клиента
     */
    @Test
    public void testUpdateCustomer() {
        CustomerRequest customer = customerRequest_1;

        assertEquals(customer.getActualAddress().getCity(), "Москва");
        assertEquals(customer.getActualAddress().getStreet(), "Новая");
        assertEquals(customer.getActualAddress().getHouse(), "11");
        assertEquals(customer.getActualAddress().getFlat(), "123");

        customer.setActualAddress(registeredAddressRequest_1);
        customerService.updateCustomer(customer);

        assertEquals(customer.getActualAddress().getCity(), "Балашиха");
        assertEquals(customer.getActualAddress().getStreet(), "Агрогородок");
        assertEquals(customer.getActualAddress().getHouse(), "7");
        assertEquals(customer.getActualAddress().getFlat(), "77");
    }

    /**
     * Поиск клиента по имени и фамилии
      */
    @Test
    public void testGetByFirstNameAndLastNameIgnoreCase() {
        FirstAndLastNameRequest nameRequest = new FirstAndLastNameRequest("анДрЕй", "ДаНИлоВ");
        Customer customer = customerService.getByFirstNameAndLastName(nameRequest);

        assertEquals(customer.getFirstName(), "Андрей");
        assertEquals(customer.getLastName(), "Данилов");
    }
}
