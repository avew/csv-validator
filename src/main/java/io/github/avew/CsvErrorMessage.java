package io.github.avew;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class CsvErrorMessage {

    public static String masa(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: masa tidak sesuai, format seharusnya 1-12", value, line, column + 1, columName);
    }

    public static String tahun(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: tahun pajak tidak cocok, seharusnya 4 digit", value, line, column + 1, columName);
    }

    public static String notNull(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: tidak boleh kosong", value, line, column + 1, columName);
    }

    public static String minLength(Object value, int line, int column, String columName, int min) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: minimal harus %d karakter.", value, line, column + 1, columName, min);
    }

    public static String nonStartZero(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: format tidak sesuai, format seharusnya tidak menggunakan awalan", value, line, column + 1, columName);
    }

    public static String isInteger(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: tidak bisa di parsing ke integer", value, line, column + 1, columName);
    }

    public static String isMonth(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: harus diisi dari angka 1 sampai 12", value, line, column + 1, columName);
    }

    public static String isBoolean(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: invalid, format yang berlaku Y atau N", value, line, column + 1, columName);
    }

    public static String minInt(Object value, int line, int column, String columName, long min) {
        return String.format(
                defaultMessage(value, line, column, columName, "minimum angka mulai dari") + ": %s",
                min
        );
    }

    public static String maxInt(Object value, int line, int column, String columName, long max) {
        return String.format(
                defaultMessage(value, line, column, columName, "maximal angka tidak lebih dari") + ":  %s",
                max
        );
    }

    public static String isBooleanInteger(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: invalid, format yang berlaku 0 atau 1", value, line, column + 1, columName);
    }

    public static String isJenisKelamin(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: invalid, format yang berlaku L atau P", value, line, column + 1, columName);
    }

    public static String isPtkp(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: invalid, format yang berlaku K,TK,K/I", value, line, column + 1, columName);
    }

    public static String isKodeNegara(Object value, int line, int col, String columnName) {
        return defaultMessage(value, line, col, columnName,
                "invalid, nilai harus merupakan kode negara");
    }

    public static String isNotNull(Object value, int line, int col, String columnName) {
        return defaultMessage(value, line, col, columnName,
                "invalid, nilai harus di isi");
    }

    public static String golongan(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: invalid, format yang berlaku I-IV", value, line, column + 1, columName);
    }

    public static String ntpn(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: NTPN harus 16 digit.", value, line, column + 1, columName);
    }

    public static String nop(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: NOP harus 18 digit.", value, line, column + 1, columName);
    }

    public static String email(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: email invalid", value, line, column + 1, columName);
    }

    public static String npwp(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: npwp invalid", value, line, column + 1, columName);
    }

    public static String tglBuktiPotongNotEquals(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: tanggal bukti potong tidak sama dengan masa dan tahun spt", value, line, column + 1, columName);
    }

    public static String tglBuktiPotongInvalid(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: tanggal bukti potong tidak bisa di parse", value, line, column + 1, columName);
    }

    public static String kodeFormEspt(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: kode form espt invalid", value, line, column + 1, columName);
    }

    public static String kodeForm22(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: kode form pasal 22 invalid", value, line, column + 1, columName);
    }

    public static String kodeForm15(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: kode form pasal 15 invalid", value, line, column + 1, columName);
    }

    public static String kodeFormEfiling(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: kode form efiling invalid", value, line, column + 1, columName);
    }

    public static String kodeTempatPenyimpananEspt(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: kode kode tempat penyimpanan espt invalid", value, line, column + 1, columName);
    }

    public static String kodeObjekPajak(Object value, int line, int column, String columName, String type) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: kode objek pajak %s invalid", value, line, column + 1, columName, type);
    }

    public static String pasal(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: kode pasal %s invalid", value, line, column + 1, columName, value);
    }

    public static String kap(Object value, int line, int column, String columName, String type) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: kode akun pajak %s invalid", value, line, column + 1, columName, type);
    }

    public static String kjs(Object value, int line, int column, String columName, String type) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: kode jenis setoran %s invalid", value, line, column + 1, columName, type);
    }


    public static String statusKaryawanA1(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: kode status pindah  invalid", value, line, column + 1, columName);
    }

    public static String statusKaryawanA2(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: kode status pindah  invalid", value, line, column + 1, columName);
    }

    public static String tipePenghasilan(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: tipe penghasilan invalid, harus diisi BG atau TBG", value, line, column + 1, columName);
    }

    public static String dipotongOleh(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: tipe penghasilan invalid, harus diisi SPK atau LSPK", value, line, column + 1, columName);
    }

    public static String caraPembayaran(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: tipe penghasilan invalid, harus diisi B atau TB", value, line, column + 1, columName);
    }

    public static String berdasarkan(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: tipe penghasilan invalid, harus diisi SPT/STP/SKPKB/SKPKBT.", value, line, column + 1, columName);
    }

    public static String kodeKeterangan(Object value, int line, int column, String columName, String type) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: kode keterangan %s invalid", value, line, column + 1, columName, type);
    }

    public static String tarifInvalid(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: tarif invalid", value, line, column + 1, columName);
    }

    public static String doubleInvalid(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: invalid number", value, line, column + 1, columName);
    }

    public static String digitsInvalid(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: harus berupa angka.", value, line, column + 1, columName);
    }

    public static String nomorBuktiPotongInvalid(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: nomor bukti potong invalid", value, line, column + 1, columName);
    }

    public static String arrays(Object value, int line, int column, String columName, String separator) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: harus dipisah dengan %s", value, line, column + 1, columName, separator);
    }

    public static String maxLength(Object value, int line, int column, String columName, int maxLength) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: maksimal %d karakter.", value, line, column + 1, columName, maxLength);
    }

    public static String isBertahapFinal(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: hanya kode objek pajak 21-401-01 & 21-401-02 yang bisa diisi Y.", value, line, column + 1, columName);
    }

    public static String password(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: harus minimal 8 karakter dan berupa kombinasi dari angka, huruf kecil, huruf besar & karakter spesial.", value, line, column + 1, columName);
    }

    public static String fasilitas(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: fasililtas invalid, harus diisi N/SKB/SKD/DTP", value, line, column + 1, columName);
    }

    public static String sspOrPbk(Object value, int line, int column, String columName) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: Jenis Bukti Penyetoran invalid, harus diisi SSP/PBK", value, line, column + 1, columName);
    }

    public static String length(Object value, int line, int column, String columName, int length) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s pesan: harus %d karakter.",
                value,
                line,
                column + 1,
                columName,
                length);
    }

    public static String defaultMessage(Object value, int line, int col, String columnName, String message) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s, pesan: %s", value, line, col, columnName, message);
    }

    public static String defaultMessage(Object value, int line, int col, String columnName, String message, Object... args) {
        return String.format("Validasi csv value: %s pada baris: %d di kolom: %d, nama kolom: %s, pesan: %s",
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
//                        " pesan: kolom %s invalid, harus diisi %s",
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
//                        " pesan: kolom %s invalid, harus diisi %s",
//                colName,
//                vals
//        );
//    }

    public static <T> String constValues(Object value, Iterable<T> enumType, int line, int col, String colName) {
        List<String> vals = new ArrayList<>();
        enumType.forEach(e -> vals.add(e.toString()));

        return String.format(
                defaultMessage(value, line, col, colName, " pesan: diisi dari salah satu %s", String.join("/", vals))
        );
    }

}
