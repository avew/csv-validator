package io.github.avew;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationCsvDTO {

    public ValidationCsvDTO(int line) {
        this(line, false, null, null);
    }

    public ValidationCsvDTO(int line, boolean error, String message) {
        this(line, error, message, null);
    }

    private int line;

    @Builder.Default
    private boolean error = false;

    private String message;

    private String code;

}
