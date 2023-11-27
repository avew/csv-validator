package io.github.avew;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class CsvUtil {

	public String[] getHeader(String headerCsv) {
		return headerCsv.replace(";", ",").split(",");
	}

	public ValidationCsvDTO headerValidation(String[] defaultHeader, String[] headerValidate) {
		ValidationCsvDTO csvValidation = new ValidationCsvDTO(1, false, "");
		if (headerValidate.length != defaultHeader.length) {
			csvValidation.setError(true);
			csvValidation.setMessage("Jumlah kolom pada header tidak sama, mohon di cek dengan template default");
		} else {
			for (int i = 0; i < headerValidate.length; i++) {
				if (defaultHeader[i] != null) {
					String header = headerValidate[i].replaceAll("[^\\x20-\\x7e]", "");
					header = header.trim().replaceAll(" +", " ");
					if (!defaultHeader[i].equalsIgnoreCase(header)) {
						csvValidation.setError(true);
						csvValidation.setMessage("Header Kolom ke " + (i + 1) + " seharusnya '" + defaultHeader[i] + "'");
					}
				}
			}
		}
		return csvValidation;
	}

	public static  <T> boolean anyMatch(Iterable<T> container, Predicate<T> matcher) {
		return indexOf(container, matcher) != -1;
	}

	public static <T> int indexOf(Iterable<T> container, Predicate<T> matcher) {
		int i = 0;
		for (T t : container) {
			if (matcher.test(t)) return i;
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
