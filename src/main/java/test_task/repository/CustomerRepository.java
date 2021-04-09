package test_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
