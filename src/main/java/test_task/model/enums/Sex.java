package test_task.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Sex {
    MALE,
    FEMALE;

    @JsonCreator
    public static Sex fromString(String key) {
        for(Sex sex : Sex.values()) {
            if(sex.name().equalsIgnoreCase(key)) {
                return sex;
            }
        }
        return null;
    }
}
