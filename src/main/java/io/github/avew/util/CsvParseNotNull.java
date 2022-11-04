package io.github.avew.util;

import io.github.avew.CsvErrorMessage;
import io.github.avew.ValidationCsvDTO;
import org.apache.commons.lang3.StringUtils;

public class CsvParseNotNull {

    public ValidationCsvDTO execute(int line, int column, Object value) {

        ValidationCsvDTO message = new ValidationCsvDTO();

        if (StringUtils.isBlank(value.toString())) {
            message.setLine(line);
            message.setError(true);
            message.setMessage(CsvErrorMessage.notNull(value, line, column));
        }
        return message;

    }
}
