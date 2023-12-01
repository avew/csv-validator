package io.github.avew;

import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CsvewResultReader<T extends CsvewValue> {

    @Builder.Default
    private List<T> values = new ArrayList<>();

    @Builder.Default
    private Collection<CsvewValidationDTO> validations = new ArrayList<>();

    @Builder.Default
    private boolean error = false;

    @Builder.Default
    private int count = 0;

    private String message;


    public Set<CsvewValidationDTO> errors() {
        if (isError()) {
            return
                    this.getValidations()
                            .stream().sorted(Comparator.comparing(CsvewValidationDTO::getLine))
                            .sorted(Comparator.comparing(CsvewValidationDTO::getColumn))
                            .collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return null;
    }

}
