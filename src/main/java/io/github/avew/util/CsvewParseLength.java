package io.github.avew.util;

import io.github.avew.CsvewErrorMessage;
import io.github.avew.CsvewValidationDTO;

public class CsvewParseLength {

    public CsvewValidationDTO execute(int line, int column, Object value, int length, String columnName) {
        CsvewValidationDTO message = new CsvewValidationDTO();

        String valueString = value.toString();

        if (valueString.length() != length) {
            message.setError(true);
            message.setLine(line);
            message.setMessage(CsvewErrorMessage.length(value, line, column, columnName, length));
        }

        return message;
    }

}
