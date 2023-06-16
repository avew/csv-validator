package io.github.avew.util;

import io.github.avew.CsvewErrorMessage;
import io.github.avew.CsvewValidationDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class CsvewParseBoolean {

    private static final List<String> BOOL_TRUE = List.of(
            "true", "t", "y", "yes", "ya", "on");
    private static final List<String> BOOL_FALSE = List.of(
            "false", "f", "n", "no", "tidak", "off");

    public CsvewValidationDTO execute(int line, int column, String columnName, Object value) {

        CsvewValidationDTO message = new CsvewValidationDTO();

        if (StringUtils.isEmpty(value.toString())) {
            message.setLine(line);
            message.setColumn(columnName);
            message.setError(true);
            message.setMessage(CsvewErrorMessage.isBoolean(value, line, column,columnName));
        }



        return message;

    }
}
