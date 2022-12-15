package io.github.avew;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class CsvErrorMessage {

    public static String masa(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: masa tidak sesuai, format seharusnya 1-12", value, line, column + 1, columName);
    }

    public static String tahun(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: tahun pajak tidak cocok, seharusnya 4 digit", value, line, column + 1, columName);
    }

    public static String notNull(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: value cannot be null", value, line, column + 1, columName);
    }

    public static String minLength(Object value, int line, int column, String columName, int min) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: must be at least %d characters.", value, line, column + 1, columName, min);
    }

    public static String nonStartZero(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: the format doesn't match, the format shouldn't use the prefix", value, line, column + 1, columName);
    }

    public static String isInteger(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: cannot be parsed to integer", value, line, column + 1, columName);
    }

    public static String isMonth(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: must be filled from numbers 1 to 12", value, line, column + 1, columName);
    }

    public static String isBoolean(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: invalid, format must Y/N", value, line, column + 1, columName);
    }

    public static String minInt(Object value, int line, int column, String columName, long min) {
        return String.format(
                defaultMessage(value, line, column, columName, "minimum number starting from") + ": %s",
                min
        );
    }

    public static String maxInt(Object value, int line, int column, String columName, long max) {
        return String.format(
                defaultMessage(value, line, column, columName, "the maximum number is not more than") + ":  %s",
                max
        );
    }

    public static String isBooleanInteger(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: invalid, valid format 0 or 1", value, line, column + 1, columName);
    }

    public static String isJenisKelamin(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: invalid, applicable format L or P", value, line, column + 1, columName);
    }

    public static String isPtkp(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: invalid, the applicable format is K, TK, K/I", value, line, column + 1, columName);
    }

    public static String isKodeNegara(Object value, int line, int col, String columnName) {
        return defaultMessage(value, line, col, columnName,
                "invalid, the value must be the country code");
    }

    public static String isNotNull(Object value, int line, int col, String columnName) {
        return defaultMessage(value, line, col, columnName,
                "invalid, value is required");
    }

    public static String golongan(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: invalid, The applicable format is I-IV", value, line, column + 1, columName);
    }

    public static String ntpn(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: NTPN must be 16 digits long.", value, line, column + 1, columName);
    }

    public static String nop(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: NOP must be 18 digits.", value, line, column + 1, columName);
    }

    public static String email(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: email invalid", value, line, column + 1, columName);
    }

    public static String npwp(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: npwp invalid", value, line, column + 1, columName);
    }

    public static String tglBuktiPotongNotEquals(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: the date is not the same as the validity period and the validity year", value, line, column + 1, columName);
    }

    public static String tglBuktiPotongInvalid(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: date could not be parsed", value, line, column + 1, columName);
    }

    public static String statusKaryawanA1(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: kode status pindah  invalid", value, line, column + 1, columName);
    }

    public static String statusKaryawanA2(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: kode status pindah  invalid", value, line, column + 1, columName);
    }

    public static String tipePenghasilan(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: tipe penghasilan invalid, harus diisi BG atau TBG", value, line, column + 1, columName);
    }

    public static String dipotongOleh(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: tipe penghasilan invalid, harus diisi SPK atau LSPK", value, line, column + 1, columName);
    }

    public static String caraPembayaran(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: tipe penghasilan invalid, harus diisi B atau TB", value, line, column + 1, columName);
    }

    public static String berdasarkan(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: tipe penghasilan invalid, harus diisi SPT/STP/SKPKB/SKPKBT.", value, line, column + 1, columName);
    }

    public static String kodeKeterangan(Object value, int line, int column, String columName, String type) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: kode keterangan %s invalid", value, line, column + 1, columName, type);
    }

    public static String tarifInvalid(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: tarif invalid", value, line, column + 1, columName);
    }

    public static String doubleInvalid(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: invalid number", value, line, column + 1, columName);
    }

    public static String digitsInvalid(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: harus berupa angka.", value, line, column + 1, columName);
    }

    public static String nomorBuktiPotongInvalid(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: nomor bukti potong invalid", value, line, column + 1, columName);
    }

    public static String arrays(Object value, int line, int column, String columName, String separator) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: harus dipisah dengan %s", value, line, column + 1, columName, separator);
    }

    public static String maxLength(Object value, int line, int column, String columName, int maxLength) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: maksimal %d karakter.", value, line, column + 1, columName, maxLength);
    }

    public static String isBertahapFinal(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: hanya kode objek pajak 21-401-01 & 21-401-02 yang bisa diisi Y.", value, line, column + 1, columName);
    }

    public static String password(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: harus minimal 8 karakter dan berupa kombinasi dari angka, huruf kecil, huruf besar & karakter spesial.", value, line, column + 1, columName);
    }

    public static String fasilitas(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: fasililtas invalid, harus diisi N/SKB/SKD/DTP", value, line, column + 1, columName);
    }

    public static String sspOrPbk(Object value, int line, int column, String columName) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: Jenis Bukti Penyetoran invalid, harus diisi SSP/PBK", value, line, column + 1, columName);
    }

    public static String length(Object value, int line, int column, String columName, int length) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s ,message: harus %d karakter.",
                value,
                line,
                column + 1,
                columName,
                length);
    }

    public static String defaultMessage(Object value, int line, int col, String columnName, String message) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s, message: %s", value, line, col, columnName, message);
    }

    public static String defaultMessage(Object value, int line, int col, String columnName, String message, Object... args) {
        return String.format("validation value: %s line: %d,column no: %d, column/field name: %s, message: %s",
                value, line, col, columnName,
                String.format(message, args));
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

        return String.format(
                defaultMessage(value, line, col, colName, " ,message: diisi dari salah satu %s", String.join("/", vals))
        );
    }

}
