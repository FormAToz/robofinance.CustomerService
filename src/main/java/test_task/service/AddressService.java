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

    public Address getFromAddressRequest(AddressRequest request) {
        return repository
                .findByCountryAndRegionAndCityAndStreetAndHouseAndFlatIgnoreCase(
                        request.getCountry(),
                        request.getRegion(),
                        request.getCity(),
                        request.getStreet(),
                        request.getHouse(),
                        request.getFlat()
                )
                .orElseThrow(()-> new IllegalArgumentException("Адрес(" + request + ") не существует"));
    }

    /**
     * Метод сохраняет новый адрес в базу данных. Если адрес уже существует - устанавливаем возвращает его из базы
     * с установленной датой изменения
     */
    public Address saveNewFromAddressRequest(AddressRequest request) {
        if (existsByAddressRequest(request)) {
            return getFromAddressRequest(request);
        }

        Address newAddress = Address.builder()
                .country(request.getCountry())
                .region(request.getRegion())
                .city(request.getCity())
                .street(request.getStreet())
                .house(request.getHouse())
                .flat(request.getFlat())
                .created(LocalDateTime.now())
                .modified(null)
                .build();

        return repository.save(newAddress);
    }

    public Address updateFromAddressRequest(AddressRequest request) {
        Address modifiedAddress = saveNewFromAddressRequest(request);
        modifiedAddress.setModified(LocalDateTime.now());
        return modifiedAddress;
    }

    public boolean existsByAddressRequest(AddressRequest request) {
        return repository.existsByCountryAndRegionAndCityAndStreetAndHouseAndFlatIgnoreCase(
                request.getCountry(),
                request.getRegion(),
                request.getCity(),
                request.getStreet(),
                request.getHouse(),
                request.getFlat()
        );
    }
}
