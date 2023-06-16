package io.github.avew;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CsvewValidationDTO {


    private int line;

    private String column;

    @Builder.Default
    private boolean error = false;

    private String message;


    @Override
    public String toString() {
        return "{" +
                "line=" + line +
                "column=" + column +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
