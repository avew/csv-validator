package io.github.avew;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class CsvParseUser extends CsvParser {

    public void parseUsername(int line,
                              int column,
                              String columnName,
                              Object o,
                              Collection<ValidationCsvDTO> validations,
                              CsvUserValueDTO value) {
        parseNotNull(line, column,columnName, o, validations);
        value.setUsername(o.toString());
    }


    public void parseFirstName(int line,
                               int column,
                               String columnName,
                               Object o,
                               Collection<ValidationCsvDTO> validations,
                               CsvUserValueDTO value) {
        parseNotNull(line, column,columnName, o, validations);
        value.setFirstname(o.toString());
    }

    public void parseLastName(int line,
                              int column,
                              String columnName,
                              Object o,
                              Collection<ValidationCsvDTO> validations,
                              CsvUserValueDTO value) {
        parseNotNull(line, column,columnName, o, validations);
        value.setLastname(o.toString());
    }
}
