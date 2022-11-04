package io.github.avew;

import java.util.Set;

@FunctionalInterface
public interface CsvValuesSerializer<T extends CsvValue> {

    void apply(int line,
               String[] columns,
               Set<ValidationCsvDTO> validations,
               T value
    ) throws Exception;

}
