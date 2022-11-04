package io.github.avew.util;


import io.github.avew.CsvErrorMessage;
import io.github.avew.ValidationCsvDTO;

import java.math.BigDecimal;

public class CsvParseBigDecimal {

    public ValidationCsvDTO execute(int line, int column, Object value, boolean notStartZero) {

        ValidationCsvDTO message = new ValidationCsvDTO();

        ValidationCsvDTO notNull = new CsvParseNotNull().execute(line, column, value);
        if (notNull.isError()) {
            message.setLine(line);
            message.setError(true);
            message.setMessage(CsvErrorMessage.notNull(value, line, column));
        }

        if (notStartZero) {
            if (value.toString().startsWith("0")) {
                message.setLine(line);
                message.setError(true);
                message.setMessage(CsvErrorMessage.nonStartZero(value, line, column));
            }
        }

        final BigDecimal result;
        if (value instanceof String) {
            try {
                result = BigDecimal.valueOf(Double.parseDouble(value.toString()));
            }
            catch (final NumberFormatException e) {
                message.setLine(line);
                message.setError(true);
                message.setMessage(CsvErrorMessage.isInteger(value, line, column));
            }
        }
        else {
            final String actualClassName = value.getClass().getName();
            message.setLine(line);
            message.setError(true);
            message.setMessage(String.format("the input value should be of type Integer or String but is of type %s", actualClassName));
        }

        return message;


    }
}
