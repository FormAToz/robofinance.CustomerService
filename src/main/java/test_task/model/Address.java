package test_task.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String region;
    private String city;
    private String street;
    private String house;
    private String flat;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime created;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime modified;

    @OneToMany(mappedBy = "registeredAddress", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Customer> registeredCustomers;

    @OneToMany(mappedBy = "actualAddress", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Customer> actualCustomers;
}
