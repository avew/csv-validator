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
    private int column;
    private Object value;

    private String columnName;

    @Builder.Default
    private boolean error = false;

    private String message;

    private String lineContent;

    @Override
    public String toString() {
        return "{" +
                "line=" + line +
                ", column_number='" + column + '\'' +
                ", column_name='" + columnName + '\'' +
                ", column_value='" + value + '\'' +
                ", message='" + message + '\'' +
                ", lineContent='" + lineContent + '\'' +
                '}';
    }
}
