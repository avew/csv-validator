package io.github.avew;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CsvValidation {

    private int line;

    private boolean error;

    @Builder.Default
    private List<String> messages = new ArrayList<>();

    public CsvValidation(int line, boolean error, List<String> messages) {
        this.line = line;
        this.error = error;
        this.messages = messages;
    }

}
