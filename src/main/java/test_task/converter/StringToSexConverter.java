package test_task.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import test_task.model.enums.Sex;

/**
 * Класс конвертации строкового значения (String) в значение пола клиента (Sex)
 */
@Component
public class StringToSexConverter implements Converter<String, Sex> {

    @Override
    public Sex convert(String s) {
        return Sex.valueOf(s.toUpperCase());
    }
}
