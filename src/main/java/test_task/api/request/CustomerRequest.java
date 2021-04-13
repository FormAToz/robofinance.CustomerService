package test_task.api.request;

import lombok.Builder;
import lombok.Data;
import test_task.model.enums.Sex;

@Builder
@Data
public class CustomerRequest {
    private AddressRequest registeredAddress;
    private AddressRequest actualAddress;
    private String firstName;
    private String lastName;
    private String middleName;
    private Sex sex;
}
