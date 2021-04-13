package test_task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @JsonIgnore
    @OneToMany(mappedBy = "registeredAddress", fetch = FetchType.LAZY)
    private List<Customer> registeredCustomers;

    @JsonIgnore
    @OneToMany(mappedBy = "actualAddress", fetch = FetchType.LAZY)
    private List<Customer> actualCustomers;

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s, %s",
                country, region, city, street, house, flat);
    }
}
