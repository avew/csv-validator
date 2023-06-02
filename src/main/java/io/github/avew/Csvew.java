package io.github.avew;

import java.util.function.Function;
import java.util.regex.Pattern;

public class Csvew {

    public String[] getHeader(String headerCsv, String delimeter) {
        return headerCsv.split(delimeter);
    }

    public CsvewValidationDTO headerValidation(String[] defaultHeader, String[] headerValidate) {
        CsvewValidationDTO csvValidation = new CsvewValidationDTO(1, false, "");
        if (headerValidate.length != defaultHeader.length) {
            csvValidation.setError(true);
            csvValidation.setMessage("The number of columns in the header is not the same, please check with the default template");
        } else {
            for (int i = 0; i < headerValidate.length; i++) {
                if (defaultHeader[i] != null) {
                    String header = headerValidate[i].replaceAll("[^\\x20-\\x7e]", "");
                    header = header.trim().replaceAll(" +", " ");
                    if (!defaultHeader[i].equalsIgnoreCase(header)) {
                        csvValidation.setError(true);
                        csvValidation.setMessage("Csv header column " + (i + 1) + " should be '" + defaultHeader[i] + "'");
                    }
                }
            }
        }
        return csvValidation;
    }

    public static <T> boolean anyMatch(Iterable<T> container, Function<T, Boolean> matcher) {
        return indexOf(container, matcher) != -1;
    }

    public static <T> int indexOf(Iterable<T> container, Function<T, Boolean> matcher) {
        int i = 0;
        for (T t : container) {
            if (matcher.apply(t)) return i;
            i++;
        }

        return -1;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

}
