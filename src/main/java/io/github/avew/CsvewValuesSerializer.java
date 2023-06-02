package io.github.avew;

import java.util.Collection;

@FunctionalInterface
public interface CsvewValuesSerializer<T extends CsvewValue> {

    void apply(int line,
               String[] columns,
               Collection<CsvewValidationDTO> validations,
               T value
    ) throws Exception;

}
