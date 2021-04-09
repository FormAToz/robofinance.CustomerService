package test_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
