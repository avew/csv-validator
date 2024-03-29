package io.github.avew;

import io.github.avew.reader.CsvewReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

public class CsvUserTest extends CsvewReader<CsvewUserValueDTO> {

    private static final String[] HEADER = {
            "username",
            "email",
            "firstname",
            "lastname"
    };


    private static void parse(int line,
                              String[] columns,
                              Collection<CsvewValidationDTO> validations,
                              CsvewUserValueDTO value,
                              CsvewParser csvParseUser) throws Exception {
        csvParseUser.parseString(line, 0, HEADER[0], columns[0], true, validations, value::setUsername);
        csvParseUser.parseEmail(line, 1, HEADER[1], columns[1], true, validations, value::setEmail);
        csvParseUser.parseString(line, 2, HEADER[2], columns[2], true, validations, value::setFirstname);
        csvParseUser.parseString(line, 3, HEADER[3], columns[3], false, validations, value::setLastname);
    }

    @Test
    public void testReadSuccess() {
        InputStream is = this.getClass().getResourceAsStream("/csv/user.csv");
        CsvewResultReader<CsvewUserValueDTO> read = process(1, is);
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
        CsvewResultReader<CsvewUserValueDTO> read = process(1, is);


        Set<CsvewValidationDTO> errors = read.errors();
        if (errors != null) errors.forEach(System.out::println);
        Assert.assertNotNull(read.errors());
    }


    @Override
    public CsvewResultReader<CsvewUserValueDTO> process(int start, InputStream is) {
        CsvewParser csvParseUser = new CsvewParser();
        return read(is, HEADER, (line, columns, validations, value) -> parse(line, columns, validations, value, csvParseUser));
    }
}
