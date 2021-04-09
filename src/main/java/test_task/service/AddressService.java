package test_task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test_task.api.request.AddressRequest;
import test_task.model.Address;
import test_task.repository.AddressRepository;

import java.time.LocalDateTime;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    public void addAddress(Address address) {
        repository.save(address);
    }

    public Address getFromAddressRequest(AddressRequest request) {
        //TODO
        return null;
    }

    public Address saveFromAddressRequest(AddressRequest request) {
        //TODO
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
        return null;
    }
}
