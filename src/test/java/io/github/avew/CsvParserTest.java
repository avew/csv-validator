package io.github.avew;

import io.github.avew.reader.CsvReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

public class CsvParserTest extends CsvReader<CsvUserValueDTO> {

    public static final String[] HEADER = {
            "username",
            "email",
            "firstname",
            "lastname"
    };

    @Test
    public void testReadSuccess() {
        InputStream is = this.getClass().getResourceAsStream("/csv/user.csv");
        CsvResultReader<CsvUserValueDTO> read = read(is);
        Assert.assertFalse(read.isError());
        if (!read.isError()) {
            read.getValues().forEach(csvUserValueDTO -> {
                System.out.println(csvUserValueDTO.toString());
            });
        }
    }

    @Test
    public void testReadFailed() {
        InputStream is = this.getClass().getResourceAsStream("/csv/user-failed.csv");
        CsvResultReader<CsvUserValueDTO> read = read(is);
        if (read.isError()) {
            read.getValidations().forEach(validationCsvDTO -> {
                System.out.println(validationCsvDTO.toString());
            });
        }
        Assert.assertTrue(read.isError());
    }

    @Override
    public CsvResultReader<CsvUserValueDTO> read(InputStream is) {
        CsvParseUser csvParseUser = new CsvParseUser();
        return read(8, is, HEADER, ";", (line, columns, validations, value) -> {
            csvParseUser.parseUsername(line, 0, HEADER[0], columns[0], validations, value);
            csvParseUser.parseEmail(line, 1, HEADER[1], columns[1], true, validations, value::setEmail);
            csvParseUser.parseFirstName(line, 2, HEADER[2], columns[2], validations, value);
            csvParseUser.parseLastName(line, 3, HEADER[3], columns[3], validations, value);
        });
    }
}
