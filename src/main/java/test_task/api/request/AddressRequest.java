package test_task.api.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddressRequest {
    private String country;
    private String region;
    private String city;
    private String street;
    private String house;
    private String flat;
    private LocalDateTime created;
    private LocalDateTime modified;
}