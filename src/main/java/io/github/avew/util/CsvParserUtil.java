package io.github.avew.util;


import io.github.avew.CsvErrorMessage;
import io.github.avew.CsvUtil;
import io.github.avew.ValidationCsvDTO;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;


public class CsvParserUtil {

    protected static final List<String> BOOL_TRUE = List.of(
            "true", "t", "y", "yes", "ya", "on");
    protected static final List<String> BOOL_FALSE = List.of(
            "false", "f", "n", "no", "tidak", "off");

    protected static final List<Integer> MONTH = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

//    protected boolean hasErrorWithCode(String code, Collection<ValidationCsvDTO> validations) {
//        return validations.stream().anyMatch(e ->
//                e.getCode().equalsIgnoreCase(code) && e.isError());
//    }

    protected ValidationCsvDTO parseNotNull(int line, int col, String columnName, Object val, Collection<ValidationCsvDTO> validations) {
        ValidationCsvDTO n = new CsvParseNotNull().execute(line, col, columnName, val);
        if (n.isError()) validations.add(n);
        return n;
    }

    protected ValidationCsvDTO parseBoolean(int line, int col, String columnName, Object o, boolean required, Collection<ValidationCsvDTO> validations) {
        ValidationCsvDTO validation = new ValidationCsvDTO();
        if (required) validation = parseNotNull(line, col, columnName, o, validations);

        validation.setLine(line);

        if (!validation.isError()) {
            String val = o.toString();
            boolean isBool = BOOL_TRUE.stream().anyMatch(val::equalsIgnoreCase) ||
                    BOOL_FALSE.stream().anyMatch(val::equalsIgnoreCase);

            if (!isBool) {
                validation.setError(true);
                ArrayList<Object> booleanType = new ArrayList<>();
                booleanType.addAll(BOOL_FALSE);
                booleanType.addAll(BOOL_TRUE);

                validation.setMessage(
                        CsvErrorMessage.constValues(val, booleanType, line, col, columnName));
            }

        }
        return validation;
    }

    protected ValidationCsvDTO parseDigit(int line, int col, String columnName, Object val, boolean isRequired, Collection<ValidationCsvDTO> validations) {
        ValidationCsvDTO n = new CsvParseDigits().execute(line, col, columnName, val, isRequired);
        if (n.isError()) validations.add(n);
        return n;
    }

    protected ValidationCsvDTO parseInteger(int line, int col, String columnName, Object o, boolean allowZeroPrefix, Collection<ValidationCsvDTO> validations) {
        ValidationCsvDTO v = new CsvParseInteger().execute(line, col, columnName, o, !allowZeroPrefix);
        if (v.isError()) validations.add(v);
        return v;
    }

    protected ValidationCsvDTO parseIntegerMin(int line, int col, String columnName, Object o, boolean required, int min, Collection<ValidationCsvDTO> validations) {
        ValidationCsvDTO m;

        if (required) {
            m = parseNotNull(line, col, columnName, o, validations);
            if (m.isError()) return m;
        }

        m = parseInteger(line, col, columnName, o, false, validations);
        if (m.isError()) return m;

        int value = Integer.parseInt(o.toString());
        if (value < min) {
            m = csvError(line, col, columnName, o, CsvErrorMessage.minInt(o, line, col, columnName, min));
            validations.add(m);
        }
        return m;
    }

    protected ValidationCsvDTO parseBigDecimal(int line, int col, String columnName, Object o, boolean allowZeroPrefix, Collection<ValidationCsvDTO> validations) {
        ValidationCsvDTO v = new CsvParseBigDecimal().execute(line, col, columnName, o, allowZeroPrefix);
        if (v.isError()) validations.add(v);
        return v;
    }

    protected ValidationCsvDTO parseRangeMin(int line, int col, String columnName, Object o, long min, boolean addToValidations, Collection<ValidationCsvDTO> validations) {
        ValidationCsvDTO message = parseDigit(line, col, columnName, o, true, validations);
        if (message.isError()) return message;

        message = parseInteger(line, col, columnName, o, false, validations);
        if (message.isError()) return message;


        message.setLine(line);
        try {
            Long num = Long.valueOf(o.toString());
            if (num < min) {
                message.setError(true);
                message.setMessage(CsvErrorMessage.minInt(o, line, col, columnName, min));
            }
        } catch (NumberFormatException e) {
            message.setError(true);
            message.setMessage(CsvErrorMessage.isInteger(o, line, col, columnName));
        }

        if (message.isError() && addToValidations) validations.add(message);
        return message;
    }

    protected ValidationCsvDTO parseRangeMax(int line, int col, String columnName, Object o, long max, boolean addToValidations, Collection<ValidationCsvDTO> validations) {
        ValidationCsvDTO message = parseDigit(line, col, columnName, o, true, validations);
        if (message.isError()) return message;

        message = parseInteger(line, col, columnName, o, false, validations);
        if (message.isError()) return message;


        message.setLine(line);
        try {
            Long num = Long.valueOf(o.toString());
            if (num < max) {
                message.setError(true);
                message.setMessage(CsvErrorMessage.maxInt(o, line, col, columnName, max));
            }
        } catch (NumberFormatException e) {
            message.setError(true);
            message.setMessage(CsvErrorMessage.isInteger(o, line, col, columnName));
        }

        if (message.isError() && addToValidations) validations.add(message);
        return message;
    }

    protected ValidationCsvDTO parseRange(int line, int col, String columnName, Object o, long minimum, long maximum, Collection<ValidationCsvDTO> validations) {
        if (minimum > maximum) // human error
            throw new IllegalArgumentException("minimum cannot be more than maximum");

        ValidationCsvDTO minValidation = parseRangeMin(line, col, columnName, o, minimum, false, validations);
        ValidationCsvDTO maxValidation = parseRangeMax(line, col, columnName, o, maximum, false, validations);

        if (minValidation.isError() || maxValidation.isError()) {
            return csvError(line, col, columnName, o,
                    "invalid, nilai seharusnya antara %s sampai dengan %s",
                    minimum, maximum);
        }

        return csvError(line, null);
    }

    protected ValidationCsvDTO csvError(int line, String message) {
        return ValidationCsvDTO.builder()
                .error(true)
                .line(line)
                .message(message)
                .build();
    }

    protected ValidationCsvDTO csvError(int line, int col, String columnName, Object value, String message, Object... args) {
        return ValidationCsvDTO.builder()
                .error(true)
                .line(line)
                .message(CsvErrorMessage.
                        defaultMessage(value, line, col, columnName, message, args))
                .build();
    }

//    public Types.Identity parseIdType(int line, int col,String columnName, Object o, Collection<ValidationCsvDTO> validations) {
//        return parseIdType(line, col, o, validations, "NPWP/NIK/TIN");
//    }
//
//    public Types.Identity parseIdType(int line, int col,String columnName, Object o, Collection<ValidationCsvDTO> validations, String columnName) {
//        String val = o.toString();
//
//        try {
//            return Types.Identity.valueOf(val);
//        } catch (IllegalArgumentException err) {
//            err.printStackTrace();
//            validations.add(
//                    validationCsvError(
//                            line,
//                            CsvErrorMessage.enumName(
//                                    val, columnName,
//                                    Types.Identity.class,
//                                    line,
//                                    col
//                            )
//                    )
//            );
//            return null;
//        }
//    }

    protected ValidationCsvDTO parseLength(int line, int column, String columnName, Object o, int length, Collection<ValidationCsvDTO> validations) {
        ValidationCsvDTO l = new CsvParseLength().execute(line, column, o, length, columnName);
        if (l.isError()) validations.add(l);
        return l;
    }

//    public Types.Facility parseFacility(int line, int col,String columnName, Object o, Collection<ValidationCsvDTO> validations) {
//        String val = o.toString();
//
//        try {
//            return Types.Facility.valueOf(val);
//        } catch (IllegalArgumentException err) {
//            err.printStackTrace();
//            validations.add(
//                    validationCsvError(line, CsvErrorMessage.fasilitas(val, line, col))
//            );
//            return null;
//        }
//    }

    protected String parseNpwpValue(int line, int column, String columnName, Object o, Collection<ValidationCsvDTO> validations) {

        parseNotNull(line, column, columnName, o, validations);
        parseLength(line, column, columnName, o, 15, validations);
        parseDigit(line, column, columnName, o, true, validations);

        if (o == null) return null;
        return o.toString();
    }

    protected String parseEmail(int line, int column, String columnName, Object o, List<ValidationCsvDTO> validations, boolean required) {
        if (required) parseNotNull(line, column, columnName, o, validations);

        return Optional.ofNullable(o)
                .map(e -> CsvUtil.isValidEmail(e.toString()) ?
                        e.toString() :
                        null)
                .orElse(null);
    }

    protected int parseMonth(int line, int column, String columnName, Object o, Collection<ValidationCsvDTO> validations) {
        ValidationCsvDTO validateResult = parseInteger(line, column, columnName, o, false, validations);
        if (!validateResult.isError()) {
            Integer v = Integer.valueOf(o.toString());
            if (!MONTH.contains(v)) {
                ValidationCsvDTO validateMonthResult = ValidationCsvDTO.builder()
                        .line(line)
                        .message(CsvErrorMessage.isInteger(v, line, column, columnName))
                        .build();
                validations.add(validateMonthResult);
            } else return v;
        }
        return -1;
    }

    protected void fillNpwpExpiredMap(String columnName, Map<String, String> npwpExpiredMap, String licenses, Collection<ValidationCsvDTO> validations) {
        if (StringUtils.isBlank(licenses)) {
            ValidationCsvDTO message = new ValidationCsvDTO();
            message.setLine(1);
            message.setError(true);
            message.setMessage(CsvErrorMessage.notNull(licenses, 1, 0, columnName));
            validations.add(message);
        } else {
            String[] licenseSplit = licenses.split(Pattern.quote("|"));

            for (String license : licenseSplit) {
                String[] npwpExpiredSplit = license.split(Pattern.quote(":"));

                npwpExpiredMap.put(npwpExpiredSplit[0], npwpExpiredSplit[1]);
            }
        }
    }

    protected void validateExpiredNpwp(int line, String sptNpwp, Function<String, LocalDate> parseExpiredDate, Map<String, String> npwpExpiredMap, Collection<ValidationCsvDTO> validations) {
        if (!npwpExpiredMap.containsKey(sptNpwp)) {
            validations.add(
                    ValidationCsvDTO.builder()
                            .line(line)
                            .error(true)
                            .message(String.format("Anda tidak memiliki akses ke NPWP: %s.", sptNpwp))
                            .build()
            );
        } else {
            String expiredDateString = npwpExpiredMap.get(sptNpwp);
            LocalDate expiredDate = parseExpiredDate.apply(expiredDateString);
            LocalDate dateValue = LocalDate.now();

            if (dateValue.isAfter(expiredDate)) {
                validations.add(
                        ValidationCsvDTO.builder()
                                .line(line)
                                .error(true)
                                .message(String.format("Lisensi untuk NPWP: %s sudah kadaluarsa pada tanggal: %s.", sptNpwp, expiredDateString))
                                .build()
                );
            }
        }
    }

}
