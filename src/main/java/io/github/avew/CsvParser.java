package io.github.avew;


import io.github.avew.util.CsvParserUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Consumer;


public class CsvParser extends CsvParserUtil {


    public void parseNotNull(int line,
                             int col,
                             Object o,
                             Collection<ValidationCsvDTO> validations,
                             CsvConsumer<String> consume) throws Exception {
        parseNotNull(line, col, o, validations);
        consume.accept(o.toString());
    }

    public void parseString(int line,
                            int col,
                            Object o,
                            boolean required,
                            Set<ValidationCsvDTO> validations,
                            CsvConsumer<String> consume) throws Exception {
        if (required) {
            parseNotNull(line, col, o, validations);
            consume.accept(o.toString());
        } else
            consume.accept(o.toString());
    }

    public void parseNullable(Object o, Consumer<String> consume) {
        consume.accept(
                StringUtils.isBlank(o.toString()) ?
                        null :
                        o.toString()
        );
    }

    public void parseBoolean(int line,
                             int col,
                             Object o,
                             boolean required,
                             Collection<ValidationCsvDTO> validations,
                             CsvConsumer<Boolean> consume) throws Exception {
        ValidationCsvDTO v = parseBoolean(line, col, o, required, validations);
        if (v.isError()) return;

        String val = o.toString();
        boolean isTrueValue = BOOL_TRUE.stream()
                .anyMatch(val::equalsIgnoreCase);
        consume.accept(isTrueValue);
    }

    public void parseInteger(int line,
                             int col,
                             Object o,
                             boolean required,
                             Collection<ValidationCsvDTO> validations,
                             CsvConsumer<Integer> consumeInt) throws Exception {
        if (required) {
            ValidationCsvDTO v = parseNotNull(line, col, o, validations);
            if (v.isError()) return;
        }

        ValidationCsvDTO v = parseInteger(line, col, o, false, validations);
        if (!v.isError()) consumeInt.accept(Integer.parseInt(o.toString()));
    }

    public void parseIntegerMin(int line,
                                int col,
                                Object o,
                                boolean required,
                                int min,
                                Collection<ValidationCsvDTO> validations,
                                CsvConsumer<Integer> consumeInt) throws Exception {
        ValidationCsvDTO m = parseIntegerMin(line, col, o, required, min, validations);
        if (m.isError()) return;

        consumeInt.accept(Integer.parseInt(o.toString()));
    }

    public void parseBigDecimal(int line,
                                int col,
                                Object o,
                                boolean allowZeroPrefix,
                                Collection<ValidationCsvDTO> validations,
                                CsvConsumer<BigDecimal> consumeBigDecimal) throws Exception {
        ValidationCsvDTO v = parseBigDecimal(line, col, o, allowZeroPrefix, validations);
        if (!v.isError())
            consumeBigDecimal.accept(new BigDecimal(o.toString()));
    }

    public void parseBigDecimalMin(int line,
                                   int col,
                                   Object o,
                                   BigDecimal min,
                                   boolean allowZeroPrefix,
                                   Collection<ValidationCsvDTO> validations,
                                   CsvConsumer<BigDecimal> consume) throws Exception {
        ValidationCsvDTO v = parseBigDecimal(line, col, o, allowZeroPrefix, validations);
        if (v.isError()) return;

        BigDecimal value = new BigDecimal(o.toString());
        if (value.compareTo(min) >= 0) {
            consume.accept(value);
            return;
        }

        validations.add(csvError(line, CsvErrorMessage.minInt(o, line, col, 0)));
    }

    public void parseRange(int line,
                           int col,
                           Object o,
                           int min, int max,
                           Collection<ValidationCsvDTO> validations,
                           CsvConsumer<String> consumeDigitRange) throws Exception {
        parseRange(line, col, o, min, max, validations);
        consumeDigitRange.accept(o.toString());
    }

    public void parseConstStr(int line,
                              int col,
                              Object o,
                              Collection<String> constants,
                              boolean required,
                              Collection<ValidationCsvDTO> validations,
                              CsvConsumer<String> consume) throws Exception {
        if (required) {
            ValidationCsvDTO v = parseNotNull(line, col, o, validations);
            if (v.isError()) return;
        }

        String s = o.toString();
        if (CsvUtil.anyMatch(constants, s::equalsIgnoreCase)) {
            // ambil nilai dari constant
            String value = new ArrayList<>(constants).get(CsvUtil.indexOf(constants, s::equalsIgnoreCase));
            consume.accept(value);
        } else {
            if (required) {
                validations.add(
                        csvError(line, CsvErrorMessage.constValues(o, constants, line, col)));
            }
        }
    }

    public void parseDigit(int line,
                           int col,
                           Object o,
                           boolean required,
                           Collection<ValidationCsvDTO> validations,
                           CsvConsumer<String> consumeDigits) throws Exception {
        ValidationCsvDTO v = parseDigit(line, col, o, required, validations);
        if (!v.isError()) consumeDigits.accept(o.toString());
    }

    public void parseDigitAndLength(int line,
                                    int col,
                                    Object o,
                                    int fixLength,
                                    boolean required,
                                    Collection<ValidationCsvDTO> validations,
                                    CsvConsumer<String> cosnumeDigitRange) throws Exception {
        parseLength(line, col, o, fixLength, validations);
        parseDigit(line, col, o, required, validations);
        cosnumeDigitRange.accept(o.toString());
    }

    public void parseNITKU(int line,
                           int col,
                           Object o,
                           Collection<ValidationCsvDTO> validations,
                           CsvConsumer<String> consumeNiku) throws Exception {
        parseLength(line, col, o, 6, validations);
        parseDigit(line, col, o, true, validations);
        consumeNiku.accept(o.toString());
    }

    public void parseMonth(int line,
                           int col,
                           Object o,
                           Collection<ValidationCsvDTO> validations,
                           CsvConsumer<Integer> consumeMasaPajak) throws Exception {
        parseNotNull(line, col, o, validations);
        int month = parseMonth(line, col, o, validations);
        if (month > 0) consumeMasaPajak.accept(month);
    }

    public void parseYear(int line,
                          int col,
                          Object o,
                          Collection<ValidationCsvDTO> validations,
                          CsvConsumer<Integer> consumeTahunPajak) throws Exception {
        parseLength(line, col, o, 4, validations);
        ValidationCsvDTO v = parseDigit(line, col, o, true, validations);

        if (!v.isError())
            consumeTahunPajak.accept(Integer.valueOf(o.toString()));
    }


    public void parseDate(int line,
                          int col,
                          Object o,
                          String dateFormat,
                          boolean required,
                          Collection<ValidationCsvDTO> validations,
                          CsvConsumer<LocalDate> consumeDate) throws Exception {

        if (required) {
            ValidationCsvDTO v = parseNotNull(line, col, o, validations);
            if (v.isError()) return;
        }

        dateFormat = Objects.requireNonNullElse(dateFormat, DATE_FORMAT);

        try {
            LocalDate date = LocalDate.parse(o.toString(), DateTimeFormatter.ofPattern(dateFormat));
            consumeDate.accept(date);
        } catch (DateTimeParseException ex) {
            if (required) {
                validations.add(csvError(line, col, o,
                        "invalid, nilai harus berupa tgl dengan format %s",
                        dateFormat
                ));
            }
        }
    }

    public void parseDateTime(int line,
                              int col,
                              Object o,
                              String dateTimeFormat,
                              boolean required,
                              Collection<ValidationCsvDTO> validations,
                              CsvConsumer<LocalDateTime> consumeDate) throws Exception {

        if (required) {
            ValidationCsvDTO v = parseNotNull(line, col, o, validations);
            if (v.isError()) return;
        }

        dateTimeFormat = Objects.requireNonNullElse(dateTimeFormat, DATE_FORMAT);

        try {
            LocalDateTime date = LocalDateTime.parse(o.toString(), DateTimeFormatter.ofPattern(dateTimeFormat));
            consumeDate.accept(date);
        } catch (DateTimeParseException ex) {
            if (required) {
                validations.add(csvError(line, col, o,
                        "invalid, nilai seharusnya berupa tgl, dengan format %s",
                        dateTimeFormat
                ));
            }
        }
    }

    public void parseKodeNegara(int line,
                                int col,
                                Object o,
                                Collection<ValidationCsvDTO> validations,
                                CsvConsumer<String> consumerKodeNegara) throws Exception {
        parseNotNull(line, col, o, validations);

        String kodeNegara = o.toString().toUpperCase();
        if (KODE_NEGARA.contains(kodeNegara))
            consumerKodeNegara.accept(kodeNegara);
    }

    public void parseEmail(int line,
                           int col,
                           Object o,
                           boolean required,
                           Collection<ValidationCsvDTO> validations,
                           CsvConsumer<String> consumeEmail) throws Exception {
        if (required) {
            ValidationCsvDTO m = parseNotNull(line, col, o, validations);
            if (m.isError()) return;
        }

        String email = o.toString();
        boolean isBlank = StringUtils.isBlank(email);
        boolean isEmail = EmailValidator.getInstance().isValid(email);

        ValidationCsvDTO message = csvError(
                line,
                CsvErrorMessage.defaultMessage(email, line, col, "Invalid email format"));

        if (required && !isEmail) {
            validations.add(message);
        } else if (!required && !isBlank && !isEmail) {
            validations.add(message);
        }

        consumeEmail.accept(email);
    }

    public void parseGender(int line,
                            int col,
                            Object o,
                            boolean required,
                            Collection<ValidationCsvDTO> validations,
                            CsvConsumer<String> consumeGender) throws Exception {
        if (required) {
            ValidationCsvDTO m = parseNotNull(line, col, o, validations);
            if (m.isError()) return;
        }

        String gender = o.toString().trim();
        boolean isBlank = StringUtils.isBlank(gender);
        boolean isGender = gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F");

        ValidationCsvDTO m = csvError(line, col, o,
                "invalid, nilai seharusnya salah 1 dari M/F");

        if (required && !isGender)
            validations.add(m);
        else if (!required && !isBlank && !isGender)
            validations.add(m);

        consumeGender.accept(gender.toUpperCase());
    }

    public void parseAplikasi(int line,
                              int column,
                              Object o,
                              Collection<ValidationCsvDTO> validations,
                              CsvConsumer<String> consume) throws Exception {
        parseNotNull(line, column, o, validations);
        String app = o.toString().toUpperCase();
        switch (app) {
            case "PPH":
                consume.accept("PPh");
            case "PPN":
                consume.accept(o.toString());
                break;
            default:
                ValidationCsvDTO m = csvError(line, column, o,
                        "invalid, nilai seharusnya salah 1 dari PPh/PPN");
                validations.add(m);
                break;
        }
    }

    public void parseTku(int line,
                         int column,
                         Object o,
                         Collection<ValidationCsvDTO> validations,
                         Consumer<String> consume) {
        parseNotNull(line, column, o, validations);
        if (o.toString().length() < 6 || o.toString().length() > 6) {
            ValidationCsvDTO m = csvError(line, column, o,
                    "invalid, nitku harus 6 karakter ");
            validations.add(m);
        } else {
            consume.accept(o.toString());
        }
    }

    public void parseKodeNegara(int line,
                                int column,
                                Object o,
                                Set<ValidationCsvDTO> validations,
                                boolean ln,
                                Consumer<String> consume) {
        String val = o.toString().toUpperCase();
        if (ln) {
            boolean countryCodeByCode = KODE_NEGARA.contains(val);
            if (countryCodeByCode) {
                consume.accept(val);
            } else {
                validations.add(csvError(line, CsvErrorMessage.isKodeNegara(o, line, column)));
            }
        }
    }


    public static final String
            DATE_FORMAT = "dd/MM/yyyy",
            DATE_TIME_FORMAT = DATE_FORMAT + " hh:mm:ss";

    public static final List<String> KODE_NEGARA = List.of(
            "ABW", "AFG", "AGO", "AIA", "ALA", "ALB", "AND", "ARE", "ARG", "ARM", "ASM", "ATA", "ATF", "ATG", "AUS", "AUT", "AZE", "BDI", "BEL", "BEN", "BES", "BFA", "BGD", "BGR", "BHR", "BHS", "BIH", "BLM", "BLR", "BLZ", "BMU", "BOL", "BRA", "BRB", "BRN", "BTN", "BVT", "BWA", "CAF", "CAN", "CCK", "CHE", "CHL", "CHN", "CIV", "CMR", "COD", "COG", "COK", "COL", "COM", "CPV", "CRI", "CUB", "CUW", "CXR", "CYM", "CZE", "DEU", "DJI", "DMA", "DNK", "DOM", "DZA", "ECU", "EGY", "ERI", "ESH", "ESP", "EST", "ETH", "FIN", "FLK", "FRA", "FRO", "FSM", "GAB", "GBR", "GEO", "GGY", "GHA", "GIB", "GIN", "GLP", "GMB", "GNB", "GRC", "GRD", "GRL", "GTM", "GUF", "GUM", "GUY", "HKG", "HMD", "HND", "HRV", "HTI", "HUN", "IDN", "IND", "IOT", "IRL", "IRN", "IRQ", "ISL", "ISR", "ITA", "JAM", "JOR", "JPN", "KAZ", "KEN", "KGZ", "KHM", "KNA", "KOR", "KWT", "LAO", "LBN", "LBR", "LBY", "LCA", "LIE", "LKA", "LSO", "LTU", "LUX", "LVA", "MAC", "MAF", "MAR", "MCO", "MDA", "MDG", "MDV", "MEX", "MHL", "MLI", "MLT", "MNE", "MNG", "MNP", "MOZ", "MRT", "MSR", "MTQ", "MUS", "MWI", "MYS", "NAM", "NCL", "NER", "NFK", "NGA", "NIC", "NIU", "NLD", "NOR", "NPL", "NRU", "NZL", "OAT", "OMN", "PAK", "PAN", "PCN", "PER", "PHL", "PLW", "PNG", "POL", "PRI", "PRK", "PRT", "PRY", "PYF", "REU", "ROU", "RUS", "RWA", "SAU", "SDN", "SEN", "SGP", "SHN", "SJM", "SLE", "SLV", "SMR", "SOM", "SPM", "SRB", "SSD", "STP", "SUR", "SVK", "SVN", "SWE", "SWZ", "SXM", "SYC", "SYR", "TCA", "TCD", "TGO", "THA", "TJK", "TKL", "TKM", "TLS", "TON", "TTO", "TUN", "TUR", "TUV", "TWN", "UGA", "UKR", "UMI", "URY", "USA", "UZB", "VAT", "VCT", "VEN", "VGB", "VIR", "VNM", "VUT", "WLF", "YEM", "ZAF", "ZMB", "ZWE");

    public static final List<String> JENIS_IDENTITAS = List.of("NIK", "NPWP");

    public static final List<String> STATUS_PTKP = List.of("T", "KI", "K");

    public static final List<String>
            FASILITAS = List.of("N", "DTP", "Lainnya", "SKD"),
            FASILITAS_MO = FASILITAS.subList(0, 2);

    public static final List<String> NIP_NRP = List.of("NIP", "NRP");
}
