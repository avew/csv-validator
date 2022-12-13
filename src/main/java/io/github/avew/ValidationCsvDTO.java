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


    private int line;

    @Builder.Default
    private boolean error = false;

    private String message;


    @Override
    public String toString() {
        return "ValidationCsvDTO{" +
                "line=" + line +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
