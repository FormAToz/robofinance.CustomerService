package test_task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import test_task.api.request.AddressRequest;
import test_task.api.request.CustomerRequest;
import test_task.api.request.FirstAndLastNameRequest;
import test_task.model.Address;
import test_task.model.Customer;
import test_task.model.enums.Sex;
import test_task.service.AddressService;
import test_task.service.CustomerService;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CustomerControllerTest {
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ Entities ++++++++++++++++++++++++++++++++++++++++++

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

    private static AddressRequest registrationAddressRequest = AddressRequest.builder()
            .country("Россия")
            .region("Московская область")
            .city("Балашиха")
            .street("Агрогородок")
            .house("7")
            .flat("77")
            .build();
    private static AddressRequest actualAddressRequest = AddressRequest.builder()
            .country("Россия")
            .region("Москва")
            .city("Москва")
            .street("Новая")
            .house("11")
            .flat("123")
            .build();
    private static CustomerRequest customerRequest1 = CustomerRequest.builder()
            .registeredAddress(registrationAddressRequest)
            .actualAddress(actualAddressRequest)
            .firstName("Андрей")
            .middleName("Сергеевич")
            .lastName("Данилов")
            .sex(Sex.MALE)
            .build();

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CustomerController customerController;
    @MockBean
    private CustomerService customerService;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testContextLoads() {
        assertThat(customerController).isNotNull();
    }

    /**
     * Получение списка всех клиентов
     */
    @Test
    public void testGetAllCustomers_thenStatus200AndCustomersListReturned() throws Exception {
        Mockito.doReturn(Arrays.asList(customer1, customer2))
                .when(customerService)
                .getAllCustomers();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    /**
     * Создание клиента
     */
    @Test
    public void testGivenCustomer_whenAdd_thenStatus200AndCustomerReturned() throws Exception {
        Mockito.doReturn(customer1)
                .when(customerService)
                .addCustomer(customerRequest1);

        mockMvc.perform(post("/add")
                .content(asJsonString(customer1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("registeredAddress")));
    }

    /**
     * Изменения данных клиента
     */
    @Test
    public void testGivenCustomer_whenUpdate_thenStatus200AndCustomerReturned() throws Exception {
        Mockito.doReturn(customer1)
                .when(customerService)
                .updateCustomer(customerRequest1);

        mockMvc.perform(put("/update")
                .content(asJsonString(customer1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    /**
     * Поиск клиента
     */
    @Test
    public void testGivenFirstAndLastNameRequest_whenSearch_thenStatus200AndCustomerReturned() throws Exception {
        Mockito.doReturn(Customer.builder().build())
                .when(customerService)
                .getByFirstNameAndLastName(new FirstAndLastNameRequest());

        mockMvc.perform(get("/search")
                .content(asJsonString(new FirstAndLastNameRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }
}
