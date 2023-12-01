package io.github.avew;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class CsvewErrorMessage {

    public static String month() {
        return "the format doesn't match, month format is 1-12";
    }

    public static String year() {
        return "the format doesn't match, year format is 4 digits";
    }


    public static String notNull() {
        return "Value cannot be null";
    }


    public static String nonStartZero() {
        return "the format doesn't match, the format shouldn't use the prefix";
    }

    public static String isInteger() {
        return "cannot be parsed to integer";
    }

    public static String isMonth() {
        return "must be filled from numbers 1 to 12";
    }

    public static String isBoolean() {
        return "invalid, format must Y/N";
    }

    public static String minInt(long min) {
        return String.format("minimum number starting from %d", min);
    }

    public static String maxInt(long max) {
        return String.format("maximum number starting from %d", max);

    }


    public static String isKodeNegara() {
        return "invalid, the value must be the country code";
    }


    public static String digitsInvalid() {
        return "must be a number";
    }


    public static String length(int length) {
        return String.format("length must be: %d character.", length);
    }


//    public static <C extends Class<? extends Enum<?>>> String enumName(Object value, String colName, C enumClass, int line, int col) {
//
//        Enum<?>[] en = enumClass.getEnumConstants();
//        String vals = Stream.of(en)
//                .map(e -> e.name())
//                .collect(Collectors.joining("/"));
//
//        return String.format(
//                defaultMessage(value, line, col, colName) +
//                        " ,message: kolom %s invalid, harus diisi %s",
//                colName,
//                vals
//        );
//    }

//    public static <T extends Class<Enum<?>>> String enumOrdinal(Object value, String colName, T enumClass, int line, int col) {
//
//        Enum<?>[] en = enumClass.getEnumConstants();
//        String vals = Stream.of(en)
//                .map(e -> Objects.toString(e.ordinal()))
//                .collect(Collectors.joining("/"));
//
//        return String.format(
//                defaultMessage(value, line, col, colName) +
//                        " ,message: kolom %s invalid, harus diisi %s",
//                colName,
//                vals
//        );
//    }

    public static <T> String constValues(Object value, Iterable<T> enumType, int line, int col, String colName) {
        List<String> vals = new ArrayList<>();
        enumType.forEach(e -> vals.add(e.toString()));
        return String.format("message: filled from either %s", String.join("/", vals));
    }

}
