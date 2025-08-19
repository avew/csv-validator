package io.github.avew;

import io.github.avew.reader.CsvewReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Collection;

public class CsvDelimetterCommaCaseTest extends CsvewReader<CsvBpReconTestDTO> {

    private static final String[] HEADER = {
            "id",
            "nomor_bupot",
            "tgl_bupot",
            "masa_pajak",
            "status_bupot",
            "lawan_transaksi",
            "nama_lt",
            "kop",
            "nitku_pemotong",
            "creation_date",
            "channel_code"
    };


    private static void parse(int line,
                              String[] cols,
                              Collection<CsvewValidationDTO> validations,
                              CsvBpReconTestDTO value,
                              CsvewParser parser) throws Exception {
        parser.parseString(line, 0, HEADER[0], cols[0], true, validations, value::setTaxId);
        parser.parseString(line, 1, HEADER[1], cols[1], false, validations, value::setTaxNo);
        parser.parseString(line, 2, HEADER[2], cols[2], true, validations, value::setTaxDate);
        parser.parseString(line, 3, HEADER[3], cols[3], true, validations, value::setTaxPeriod);
        parser.parseString(line, 4, HEADER[4], cols[4], true, validations, value::setBpStatus);
        parser.parseString(line, 5, HEADER[5], cols[5], true, validations, value::setIdentityNo);
        parser.parseString(line, 6, HEADER[6], cols[6], true, validations, value::setIdentityName);
        parser.parseString(line, 7, HEADER[7], cols[7], true, validations, value::setKop);
        parser.parseString(line, 8, HEADER[8], cols[8], true, validations, value::setNitkuPemotong);
    }

    @Test
    public void testReadSuccess() {
        InputStream is = this.getClass().getResourceAsStream("/csv/contoh data koma GENTA.csv");
        CsvewResultReader<CsvBpReconTestDTO> read = process(1, is);
        if (!read.isError()) {
            read.getValues().forEach(csvUserValueDTO -> {
                System.out.println(csvUserValueDTO.toString());
            });
        }else {
            read.getValidations().forEach(validation -> {
                System.out.println("Error: " + validation.getMessage());
            });
        }
    }


    @Override
    public CsvewResultReader<CsvBpReconTestDTO> process(int start, InputStream is) {
        CsvewParser csvParseUser = new CsvewParser();
        return readWithQuotedValues(0, is, HEADER, ",", (line, columns, validations, value) -> parse(line, columns, validations, value, csvParseUser));
    }
}
