package io.github.avew;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

}
