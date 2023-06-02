package io.github.avew.util;


import io.github.avew.CsvewErrorMessage;
import io.github.avew.CsvewValidationDTO;

import java.math.BigDecimal;

public class CsvewParseBigDecimal {

    public CsvewValidationDTO execute(int line, int column, String columnName, Object value, boolean notStartZero) {

        CsvewValidationDTO message = new CsvewValidationDTO();

        CsvewValidationDTO notNull = new CsvewParseNotNull().execute(line, column, columnName, value);
        if (notNull.isError()) {
            message.setLine(line);
            message.setError(true);
            message.setMessage(CsvewErrorMessage.notNull(value, line, column, columnName));
        }

        if (notStartZero) {
            if (value.toString().startsWith("0")) {
                message.setLine(line);
                message.setError(true);
                message.setMessage(CsvewErrorMessage.nonStartZero(value, line, column, columnName));
            }
        }

        final BigDecimal result;
        if (value instanceof String) {
            try {
                result = BigDecimal.valueOf(Double.parseDouble(value.toString()));
            } catch (final NumberFormatException e) {
                message.setLine(line);
                message.setError(true);
                message.setMessage(CsvewErrorMessage.isInteger(value, line, column, columnName));
            }
        } else {
            final String actualClassName = value.getClass().getName();
            message.setLine(line);
            message.setError(true);
            message.setMessage(String.format("the input value should be of type Integer or String but is of type %s", actualClassName));
        }

        return message;


    }
}
