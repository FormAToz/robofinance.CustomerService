package test_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Address;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByCountryAndRegionAndCityAndStreetAndHouseAndFlatIgnoreCase(
            String country,
            String region,
            String city,
            String street,
            String house,
            String flat
    );

    boolean existsByCountryAndRegionAndCityAndStreetAndHouseAndFlatIgnoreCase(
            String country,
            String region,
            String city,
            String street,
            String house,
            String flat
    );
}
