package io.github.avew.util;

import io.github.avew.CsvErrorMessage;
import io.github.avew.ValidationCsvDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class CsvParseBoolean {

    private static final List<String> BOOL_TRUE = List.of(
            "true", "t", "y", "yes", "ya", "on");
    private static final List<String> BOOL_FALSE = List.of(
            "false", "f", "n", "no", "tidak", "off");

    public ValidationCsvDTO execute(int line, int column, Object value) {

        ValidationCsvDTO message = new ValidationCsvDTO();

        if (StringUtils.isEmpty(value.toString())) {
            message.setLine(line);
            message.setError(true);
            message.setMessage(CsvErrorMessage.isBoolean(value, line, column));
        }



        return message;

    }
}
