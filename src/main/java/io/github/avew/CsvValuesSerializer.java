package io.github.avew;

import java.util.Collection;

@FunctionalInterface
public interface CsvValuesSerializer<T extends CsvValue> {

    void apply(int line,
               String[] columns,
               Collection<ValidationCsvDTO> validations,
               T value
    ) throws Exception;

}
