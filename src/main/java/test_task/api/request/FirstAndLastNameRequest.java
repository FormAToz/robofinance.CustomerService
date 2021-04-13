package test_task.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FirstAndLastNameRequest {
    private String firstName;
    private String lastName;
}
