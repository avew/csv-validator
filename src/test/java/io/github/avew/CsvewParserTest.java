package io.github.avew;

import io.github.avew.reader.CsvewReader;
import lombok.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

public class CsvewParserTest extends CsvewReader<CsvewUserValueDTO> {

    private static final String[] HEADER = {
            "username",
            "email",
            "firstname",
            "lastname"
    };

    @Test
    public void testReadSuccess() {
        InputStream is = this.getClass().getResourceAsStream("/csv/user.csv");
        CsvewResultReader<CsvewUserValueDTO> read = process(2, is);
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
        CsvewResultReader<CsvewUserValueDTO> read = process(0, is);
        if (read.isError()) {
            read.getValidations().forEach(validationCsvDTO -> {
                System.out.println(validationCsvDTO.toString());
            });
        }
        Assert.assertTrue(read.isError());
    }

    @Override
    public CsvewResultReader<CsvewUserValueDTO> process(int startAt, InputStream is) {
        CsvewParser csvParseUser = new CsvewParser();
        return read(true, startAt, is, HEADER, ";", (line, columns, validations, value) -> {
            csvParseUser.parseString(line, 0, HEADER[0], columns[0], true, validations, value::setUsername);
            csvParseUser.parseEmail(line, 1, HEADER[1], columns[1], true, validations, value::setEmail);
            csvParseUser.parseString(line, 2, HEADER[2], columns[2], false, validations, value::setFirstname);
            csvParseUser.parseString(line, 3, HEADER[3], columns[3], false, validations, value::setLastname);
        });
    }


}
