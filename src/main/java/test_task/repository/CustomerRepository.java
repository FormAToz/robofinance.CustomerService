package test_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Address;
import test_task.model.Customer;
import test_task.model.enums.Sex;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByActualAddress(Address address);

    Optional<Customer> findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);

    Optional<Customer> findByFirstNameAndMiddleNameAndLastNameAndSexAndRegisteredAddress(
            String firstName,
            String middleName,
            String lastName,
            Sex sex,
            Address registeredAddress);
}
