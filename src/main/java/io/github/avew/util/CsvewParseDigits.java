package io.github.avew.util;

import io.github.avew.CsvewErrorMessage;
import io.github.avew.CsvewValidationDTO;
import org.apache.commons.lang3.math.NumberUtils;

public class CsvewParseDigits {

    public CsvewValidationDTO execute(int line,
                                      int column,
                                      String columnName,
                                      Object value,
                                      boolean isRequired) {
        CsvewValidationDTO message = new CsvewValidationDTO();

        if (isRequired) {
            CsvewValidationDTO notNull = new CsvewParseNotNull().execute(line, column, columnName, value);

            if (notNull.isError()) {
                message.setError(true);
                message.setLine(line);
                message.setColumn(columnName);
                message.setMessage(CsvewErrorMessage.notNull(value, line, column, columnName));
            }
        }

        if (value != null) {
            String valueString = value.toString().trim();
            if (!valueString.isEmpty()) {
                if (!NumberUtils.isDigits(valueString)) {
                    message.setError(true);
                    message.setLine(line);
                    message.setColumn(columnName);
                    message.setMessage(CsvewErrorMessage.digitsInvalid(value, line, column, columnName));
                }
            }
        }

        return message;
    }

}