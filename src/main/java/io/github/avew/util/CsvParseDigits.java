package io.github.avew.util;

import io.github.avew.CsvErrorMessage;
import io.github.avew.ValidationCsvDTO;
import org.apache.commons.lang3.math.NumberUtils;

public class CsvParseDigits {

    public ValidationCsvDTO execute(int line,
                                    int column,
                                    String columnName,
                                    Object value,
                                    boolean isRequired) {
        ValidationCsvDTO message = new ValidationCsvDTO();

        if (isRequired) {
            ValidationCsvDTO notNull = new CsvParseNotNull().execute(line, column, columnName, value);

            if (notNull.isError()) {
                message.setError(true);
                message.setLine(line);
                message.setMessage(CsvErrorMessage.notNull(value, line, column, columnName));
            }
        }

        if (value != null) {
            String valueString = value.toString().trim();
            if (!valueString.isEmpty()) {
                if (!NumberUtils.isDigits(valueString)) {
                    message.setError(true);
                    message.setLine(line);
                    message.setMessage(CsvErrorMessage.digitsInvalid(value, line, column, columnName));
                }
            }
        }

        return message;
    }

}