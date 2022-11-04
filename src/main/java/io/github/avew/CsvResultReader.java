package io.github.avew;

import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CsvResultReader<T extends CsvValue> {

    @Builder.Default
    private List<T> values = new ArrayList<>();

    @Builder.Default
    private Set<ValidationCsvDTO> validations = new HashSet<>();

    @Builder.Default
    private boolean error = false;

    @Builder.Default
    private int count = 0;

    private String message;

}
