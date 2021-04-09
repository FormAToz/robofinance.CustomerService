package test_task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test_task.model.enums.Sex;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(columnDefinition = "VARCHAR(6)")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "registered_address_id")
    private Address registeredAddress;

    @ManyToOne
    @JoinColumn(name = "actual_address_id")
    private Address actualAddress;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id) &&
                lastName.equals(customer.lastName) &&
                firstName.equals(customer.firstName) &&
                middleName.equals(customer.middleName) &&
                sex == customer.sex &&
                registeredAddress.equals(customer.registeredAddress) &&
                actualAddress.equals(customer.actualAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, middleName, sex, registeredAddress, actualAddress);
    }
}
