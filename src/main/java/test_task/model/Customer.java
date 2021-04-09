package test_task.model;

import lombok.*;
import test_task.model.enums.Sex;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_address_id")
    private Address registeredAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actual_address_id")
    private Address actualAddress;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(columnDefinition = "VARCHAR(6)")
    @Enumerated(EnumType.STRING)
    private Sex sex;
}
