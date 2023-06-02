package io.github.avew.util;

import io.github.avew.CsvewErrorMessage;
import io.github.avew.CsvewValidationDTO;
import org.apache.commons.lang3.StringUtils;

public class CsvewParseNotNull {

    public CsvewValidationDTO execute(int line, int column, String columnName, Object value) {

        CsvewValidationDTO message = new CsvewValidationDTO();

        if (StringUtils.isBlank(value.toString())) {
            message.setLine(line);
            message.setError(true);
            message.setMessage(CsvewErrorMessage.notNull(value, line, column, columnName));
        }
        return message;

    }
}
