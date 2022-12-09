package io.github.avew.util;

import io.github.avew.CsvErrorMessage;
import io.github.avew.ValidationCsvDTO;

public class CsvParseLength {

    public ValidationCsvDTO execute(int line, int column, Object value, int length, String columnName) {
        ValidationCsvDTO message = new ValidationCsvDTO();

        String valueString = value.toString();

        if (valueString.length() != length) {
            message.setError(true);
            message.setLine(line);
            message.setMessage(CsvErrorMessage.length(value, line, column, columnName, length));
        }

        return message;
    }

}
