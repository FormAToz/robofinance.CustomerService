package test_task.api.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddressRequest {
    private String country;
    private String region;
    private String city;
    private String street;
    private String house;
    private String flat;

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s, %s",
                country, region, city, street, house, flat);
    }
}