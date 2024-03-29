package io.github.avew.util;

import io.github.avew.CsvewErrorMessage;
import io.github.avew.CsvewValidationDTO;

public class CsvewParseInteger {

    public CsvewValidationDTO execute(int line, int column, String columnName, Object value, boolean noZeroPrefix) {

        CsvewValidationDTO message = new CsvewValidationDTO();

        CsvewValidationDTO notNull = new CsvewParseNotNull().execute(line, column, columnName, value);
        if (notNull.isError()) {
            message.setLine(line);
            message.setColumn(column);
            message.setColumnName(columnName);
            message.setError(true);
            message.setMessage(CsvewErrorMessage.notNull());
        }

        if (noZeroPrefix) {
            if (value.toString().startsWith("0")) {
                message.setLine(line);
                message.setColumn(column);
                message.setColumnName(columnName);
                message.setError(true);
                message.setMessage(CsvewErrorMessage.nonStartZero());
            }
        }

        if (value instanceof Integer) {
            return message;
        } else if (value instanceof String) {
            try {
                Integer.valueOf((String) value);
            } catch (final NumberFormatException e) {
                message.setLine(line);
                message.setColumn(column);
                message.setColumnName(columnName);
                message.setError(true);
                message.setMessage(CsvewErrorMessage.isInteger());
            }
        } else {
            final String actualClassName = value.getClass().getName();
            message.setLine(line);
            message.setColumn(column);
            message.setColumnName(columnName);
            message.setError(true);
            message.setMessage(String.format("the input value should be of type Integer or String but is of type %s", actualClassName));
        }

        return message;


    }


}
