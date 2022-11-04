package io.github.avew;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("SpellCheckingInspection")
public class CsvErrorMessage {

	public static String masa(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: masa tidak sesuai, format seharusnya 1-12", value, line, column + 1);
	}

	public static String tahun(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: tahun pajak tidak cocok, seharusnya 4 digit", value, line, column + 1);
	}

	public static String notNull(Object value, int line, int column) {
		return String.format("[csv] value %s pada baris %d di kolom %d pesan: tidak boleh kosong", value, line, column + 1);
	}

	public static String minLength(Object value, int line, int column, int min) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: minimal harus '%d' karakter.", value, line, column + 1, min);
	}

	public static String nonStartZero(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: format tidak sesuai, format seharusnya tidak menggunakan awalan", value, line, column + 1);
	}

	public static String isInteger(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: tidak bisa di parsing ke integer", value, line, column + 1);
	}

	public static String isMonth(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: harus diisi dari angka 1 sampai 12", value, line, column + 1);
	}

	public static String isBoolean(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: invalid, format yang berlaku Y atau N", value, line, column + 1);
	}

	public static String minInt(Object value, int line, int column, long min) {
		return String.format(
				defaultMessage(value, line, column) + ": minimum angka mulai dari %s",
				min
		);
	}

	public static String maxInt(Object value, int line, int column, long max) {
		return String.format(
				defaultMessage(value, line, column) + ": maximal angka tidak lebih dari %s",
				max
		);
	}

	public static String isBooleanInteger(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: invalid, format yang berlaku 0 atau 1", value, line, column + 1);
	}

	public static String isJenisKelamin(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: invalid, format yang berlaku L atau P", value, line, column + 1);
	}

	public static String isPtkp(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: invalid, format yang berlaku K,TK,K/I", value, line, column + 1);
	}

	public static String isKodeNegara(Object value, int line, int col) {
		return defaultMessage(value, line, col,
                "invalid, nilai harus merupakan kode negara");
	}

	public static String isNotNull(Object value, int line, int col) {
		return defaultMessage(value, line, col,
                "invalid, nilai harus di isi");
	}

	public static String golongan(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: invalid, format yang berlaku I-IV", value, line, column + 1);
	}

	public static String ntpn(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: NTPN harus 16 digit.", value, line, column + 1);
	}

	public static String nop(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: NOP harus 18 digit.", value, line, column + 1);
	}

	public static String email(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: email invalid", value, line, column + 1);
	}

	public static String npwp(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: npwp invalid", value, line, column + 1);
	}

	public static String tglBuktiPotongNotEquals(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: tanggal bukti potong tidak sama dengan masa dan tahun spt", value, line, column + 1);
	}

	public static String tglBuktiPotongInvalid(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: tanggal bukti potong tidak bisa di parse", value, line, column + 1);
	}

	public static String kodeFormEspt(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: kode form espt invalid", value, line, column + 1);
	}

	public static String kodeForm22(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: kode form pasal 22 invalid", value, line, column + 1);
	}

	public static String kodeForm15(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: kode form pasal 15 invalid", value, line, column + 1);
	}

	public static String kodeFormEfiling(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: kode form efiling invalid", value, line, column + 1);
	}

	public static String kodeTempatPenyimpananEspt(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: kode kode tempat penyimpanan espt invalid", value, line, column + 1);
	}

	public static String kodeObjekPajak(Object value, int line, int column, String type) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: kode objek pajak '%s' invalid", value, line, column + 1, type);
	}

	public static String pasal(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: kode pasal '%s' invalid", value, line, column + 1, value);
	}

	public static String kap(Object value, int line, int column, String type) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: kode akun pajak '%s' invalid", value, line, column + 1, type);
	}

	public static String kjs(Object value, int line, int column, String type) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: kode jenis setoran '%s' invalid", value, line, column + 1, type);
	}



	public static String statusKaryawanA1(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: kode status pindah  invalid", value, line, column + 1);
	}

	public static String statusKaryawanA2(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: kode status pindah  invalid", value, line, column + 1);
	}

	public static String tipePenghasilan(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: tipe penghasilan invalid, harus diisi BG atau TBG", value, line, column + 1);
	}

	public static String dipotongOleh(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: tipe penghasilan invalid, harus diisi SPK atau LSPK", value, line, column + 1);
	}

	public static String caraPembayaran(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: tipe penghasilan invalid, harus diisi B atau TB", value, line, column + 1);
	}

	public static String berdasarkan(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: tipe penghasilan invalid, harus diisi SPT/STP/SKPKB/SKPKBT.", value, line, column + 1);
	}

	public static String kodeKeterangan(Object value, int line, int column, String type) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: kode keterangan '%s' invalid", value, line, column + 1, type);
	}

	public static String tarifInvalid(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: tarif invalid", value, line, column + 1);
	}

	public static String doubleInvalid(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: invalid number", value, line, column + 1);
	}

	public static String digitsInvalid(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: harus berupa angka.", value, line, column + 1);
	}

	public static String nomorBuktiPotongInvalid(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: nomor bukti potong invalid", value, line, column + 1);
	}

	public static String arrays(Object value, int line, int column, String separator) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: harus dipisah dengan '%s'", value, line, column + 1, separator);
	}

	public static String maxLength(Object value, int line, int column, int maxLength) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: maksimal '%d' karakter.", value, line, column + 1, maxLength);
	}

	public static String isBertahapFinal(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: hanya kode objek pajak 21-401-01 & 21-401-02 yang bisa diisi Y.", value, line, column + 1);
	}

	public static String password(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: harus minimal 8 karakter dan berupa kombinasi dari angka, huruf kecil, huruf besar & karakter spesial.", value, line, column + 1);
	}

	public static String fasilitas(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: fasililtas invalid, harus diisi N/SKB/SKD/DTP", value, line, column + 1);
	}

	public static String sspOrPbk(Object value, int line, int column) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: Jenis Bukti Penyetoran invalid, harus diisi SSP/PBK", value, line, column + 1);
	}

	public static String length(Object value, int line, int column, int length) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d' pesan: harus '%d' karakter.",
				value,
				line,
				column + 1,
				length);
	}

	public static String defaultMessage(Object value, int line, int col) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d'", value, line, col);
	}

	public static String defaultMessage(Object value, int line, int col, String message, Object... args) {
		return String.format("[csv] value '%s' pada baris '%d' di kolom '%d', pesan: %s",
				value, line, col,
				String.format(message, args));
	}

	public static <C extends Class<? extends Enum<?>>> String enumName(Object value, String colName, C enumClass, int line, int col) {

		Enum<?>[] en = enumClass.getEnumConstants();
		String vals = Stream.of(en)
				.map(e -> e.name())
				.collect(Collectors.joining("/"));

		return String.format(
				defaultMessage(value, line, col) +
						" pesan: kolom '%s' invalid, harus diisi %s",
				colName,
				vals
		);
	}
	public static <T extends Class<Enum<?>>> String enumOrdinal(Object value, String colName, T enumClass, int line, int col) {

		Enum<?>[] en = enumClass.getEnumConstants();
		String vals = Stream.of(en)
				.map(e -> Objects.toString(e.ordinal()))
				.collect(Collectors.joining("/"));

		return String.format(
				defaultMessage(value, line, col) +
						" pesan: kolom %s invalid, harus diisi %s",
				colName,
				vals
		);
	}
	public static <T> String constValues(Object value, Iterable<T> enumType, int line, int col) {
		List<String> vals = new ArrayList<>();
		enumType.forEach(e -> vals.add(e.toString()));

		return String.format(
				defaultMessage(value, line, col) +
						" pesan: diisi dari salah satu %s", String.join("/", vals)
		);
	}

}
