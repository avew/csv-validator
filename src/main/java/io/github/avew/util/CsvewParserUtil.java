package io.github.avew.util;


import io.github.avew.CsvewErrorMessage;
import io.github.avew.Csvew;
import io.github.avew.CsvewValidationDTO;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;


public class CsvewParserUtil {

    protected static final List<String> BOOL_TRUE = List.of(
            "true", "t", "y", "yes", "ya", "on");
    protected static final List<String> BOOL_FALSE = List.of(
            "false", "f", "n", "no", "tidak", "off");

    protected static final List<Integer> MONTH = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

//    protected boolean hasErrorWithCode(String code, Collection<ValidationCsvDTO> validations) {
//        return validations.stream().anyMatch(e ->
//                e.getCode().equalsIgnoreCase(code) && e.isError());
//    }

    protected CsvewValidationDTO parseNotNull(int line, int col, String columnName, Object val, Collection<CsvewValidationDTO> validations) {
        CsvewValidationDTO n = new CsvewParseNotNull().execute(line, col, columnName, val);
        if (n.isError()) validations.add(n);
        return n;
    }

    protected CsvewValidationDTO parseBoolean(int line, int col, String columnName, Object o, boolean required, Collection<CsvewValidationDTO> validations) {
        CsvewValidationDTO validation = new CsvewValidationDTO();
        if (required) validation = parseNotNull(line, col, columnName, o, validations);

        validation.setLine(line);
        validation.setColumn(columnName);

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
                        CsvewErrorMessage.constValues(val, booleanType, line, col, columnName));
            }

        }
        return validation;
    }

    protected CsvewValidationDTO parseDigit(int line, int col, String columnName, Object val, boolean isRequired, Collection<CsvewValidationDTO> validations) {
        CsvewValidationDTO n = new CsvewParseDigits().execute(line, col, columnName, val, isRequired);
        if (n.isError()) validations.add(n);
        return n;
    }

    protected CsvewValidationDTO parseInteger(int line, int col, String columnName, Object o, boolean allowZeroPrefix, Collection<CsvewValidationDTO> validations) {
        CsvewValidationDTO v = new CsvewParseInteger().execute(line, col, columnName, o, !allowZeroPrefix);
        if (v.isError()) validations.add(v);
        return v;
    }

    protected CsvewValidationDTO parseIntegerMin(int line, int col, String columnName, Object o, boolean required, int min, Collection<CsvewValidationDTO> validations) {
        CsvewValidationDTO m;

        if (required) {
            m = parseNotNull(line, col, columnName, o, validations);
            if (m.isError()) return m;
        }

        m = parseInteger(line, col, columnName, o, false, validations);
        if (m.isError()) return m;

        int value = Integer.parseInt(o.toString());
        if (value < min) {
            m = csvError(line, col, columnName, o, CsvewErrorMessage.minInt(o, line, col, columnName, min));
            validations.add(m);
        }
        return m;
    }

    protected CsvewValidationDTO parseBigDecimal(int line, int col, String columnName, Object o, boolean allowZeroPrefix, Collection<CsvewValidationDTO> validations) {
        CsvewValidationDTO v = new CsvewParseBigDecimal().execute(line, col, columnName, o, allowZeroPrefix);
        if (v.isError()) validations.add(v);
        return v;
    }

    protected CsvewValidationDTO parseRangeMin(int line, int col, String columnName, Object o, long min, boolean addToValidations, Collection<CsvewValidationDTO> validations) {
        CsvewValidationDTO message = parseDigit(line, col, columnName, o, true, validations);
        if (message.isError()) return message;

        message = parseInteger(line, col, columnName, o, false, validations);
        if (message.isError()) return message;


        message.setLine(line);
        message.setColumn(columnName);
        try {
            Long num = Long.valueOf(o.toString());
            if (num < min) {
                message.setError(true);
                message.setMessage(CsvewErrorMessage.minInt(o, line, col, columnName, min));
            }
        } catch (NumberFormatException e) {
            message.setError(true);
            message.setMessage(CsvewErrorMessage.isInteger(o, line, col, columnName));
        }

        if (message.isError() && addToValidations) validations.add(message);
        return message;
    }

    protected CsvewValidationDTO parseRangeMax(int line, int col, String columnName, Object o, long max, boolean addToValidations, Collection<CsvewValidationDTO> validations) {
        CsvewValidationDTO message = parseDigit(line, col, columnName, o, true, validations);
        if (message.isError()) return message;

        message = parseInteger(line, col, columnName, o, false, validations);
        if (message.isError()) return message;


        message.setLine(line);
        message.setColumn(columnName);
        try {
            Long num = Long.valueOf(o.toString());
            if (num < max) {
                message.setError(true);
                message.setMessage(CsvewErrorMessage.maxInt(o, line, col, columnName, max));
            }
        } catch (NumberFormatException e) {
            message.setError(true);
            message.setMessage(CsvewErrorMessage.isInteger(o, line, col, columnName));
        }

        if (message.isError() && addToValidations) validations.add(message);
        return message;
    }

    protected CsvewValidationDTO parseRange(int line, int col, String columnName, Object o, long minimum, long maximum, Collection<CsvewValidationDTO> validations) {
        if (minimum > maximum) // human error
            throw new IllegalArgumentException("minimum cannot be more than maximum");

        CsvewValidationDTO minValidation = parseRangeMin(line, col, columnName, o, minimum, false, validations);
        CsvewValidationDTO maxValidation = parseRangeMax(line, col, columnName, o, maximum, false, validations);

        if (minValidation.isError() || maxValidation.isError()) {
            return csvError(line, col, columnName, o,
                    "invalid, nilai seharusnya antara %s sampai dengan %s",
                    minimum, maximum);
        }

        return csvError(line, null);
    }

    protected CsvewValidationDTO csvError(int line, String message) {
        return CsvewValidationDTO.builder()
                .error(true)
                .line(line)
                .message(message)
                .build();
    }

    protected CsvewValidationDTO csvError(int line, int col, String columnName, Object value, String message, Object... args) {
        return CsvewValidationDTO.builder()
                .error(true)
                .line(line)
                .message(CsvewErrorMessage.
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

    protected CsvewValidationDTO parseLength(int line, int column, String columnName, Object o, int length, Collection<CsvewValidationDTO> validations) {
        CsvewValidationDTO l = new CsvewParseLength().execute(line, column, o, length, columnName);
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

    protected String parseNpwpValue(int line, int column, String columnName, Object o, Collection<CsvewValidationDTO> validations) {

        parseNotNull(line, column, columnName, o, validations);
        parseLength(line, column, columnName, o, 15, validations);
        parseDigit(line, column, columnName, o, true, validations);

        if (o == null) return null;
        return o.toString();
    }

    protected String parseEmail(int line, int column, String columnName, Object o, List<CsvewValidationDTO> validations, boolean required) {
        if (required) parseNotNull(line, column, columnName, o, validations);

        return Optional.ofNullable(o)
                .map(e -> Csvew.isValidEmail(e.toString()) ?
                        e.toString() :
                        null)
                .orElse(null);
    }

    protected int parseMonth(int line, int column, String columnName, Object o, Collection<CsvewValidationDTO> validations) {
        CsvewValidationDTO validateResult = parseInteger(line, column, columnName, o, false, validations);
        if (!validateResult.isError()) {
            Integer v = Integer.valueOf(o.toString());
            if (!MONTH.contains(v)) {
                CsvewValidationDTO validateMonthResult = CsvewValidationDTO.builder()
                        .line(line)
                        .message(CsvewErrorMessage.isInteger(v, line, column, columnName))
                        .build();
                validations.add(validateMonthResult);
            } else return v;
        }
        return -1;
    }

    protected void fillNpwpExpiredMap(String columnName, Map<String, String> npwpExpiredMap, String licenses, Collection<CsvewValidationDTO> validations) {
        if (StringUtils.isBlank(licenses)) {
            CsvewValidationDTO message = new CsvewValidationDTO();
            message.setLine(1);
            message.setColumn(columnName);
            message.setError(true);
            message.setMessage(CsvewErrorMessage.notNull(licenses, 1, 0, columnName));
            validations.add(message);
        } else {
            String[] licenseSplit = licenses.split(Pattern.quote("|"));

            for (String license : licenseSplit) {
                String[] npwpExpiredSplit = license.split(Pattern.quote(":"));

                npwpExpiredMap.put(npwpExpiredSplit[0], npwpExpiredSplit[1]);
            }
        }
    }

    protected void validateExpiredNpwp(int line, String sptNpwp, Function<String, LocalDate> parseExpiredDate, Map<String, String> npwpExpiredMap, Collection<CsvewValidationDTO> validations) {
        if (!npwpExpiredMap.containsKey(sptNpwp)) {
            validations.add(
                    CsvewValidationDTO.builder()
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
                        CsvewValidationDTO.builder()
                                .line(line)
                                .error(true)
                                .message(String.format("Lisensi untuk NPWP: %s sudah kadaluarsa pada tanggal: %s.", sptNpwp, expiredDateString))
                                .build()
                );
            }
        }
    }

}
