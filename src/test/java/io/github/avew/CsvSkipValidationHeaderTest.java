package io.github.avew;

import io.github.avew.reader.CsvewReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Collection;

public class CsvSkipValidationHeaderTest extends CsvewReader<CsvewUserValueDTO> {

    public static final String[] HEADER = {
            "username",   // 0
            "email",      // 1
            "firstname",  // 2
            "lastname"    // 3
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

        // Parse optional ref1 and ref2 if they exist
        if (columns.length > 4) {
            csvParseUser.parseString(line, 4, "ref1", columns[4], false, validations, value::setRef1);
        }
        if (columns.length > 5) {
            csvParseUser.parseString(line, 5, "ref2", columns[5], false, validations, value::setRef2);
        }
    }

    /**
     * Test case: CSV dengan kolom tambahan ref1 dan ref2
     * Harus berhasil dan data ref1, ref2 terambil
     */
    @Test
    public void testReadWithExtraColumns() {
        InputStream is = this.getClass().getResourceAsStream("/csv/user-with-extra-columns.csv");
        CsvewResultReader<CsvewUserValueDTO> read = process(1, is);

        Assert.assertFalse("Should not have errors", read.isError());
        Assert.assertEquals("Should have 3 records", 3, read.getCount());

        // Verify first record
        CsvewUserValueDTO firstUser = read.getValues().get(0);
        Assert.assertEquals("john1", firstUser.getUsername());
        Assert.assertEquals("john1@mail.com", firstUser.getEmail());
        Assert.assertEquals("John", firstUser.getFirstname());
        Assert.assertEquals("Doe", firstUser.getLastname());
        Assert.assertEquals("REF001", firstUser.getRef1());
        Assert.assertEquals("REF002", firstUser.getRef2());

        System.out.println("=== Test With Extra Columns ===");
        read.getValues().forEach(System.out::println);
    }

    /**
     * Test case: CSV tanpa kolom tambahan (hanya kolom standar)
     * Harus tetap berhasil tanpa error
     */
    @Test
    public void testReadWithStandardColumns() {
        InputStream is = this.getClass().getResourceAsStream("/csv/user-standard-columns.csv");
        CsvewResultReader<CsvewUserValueDTO> read = process(1, is);

        Assert.assertFalse("Should not have errors", read.isError());
        Assert.assertEquals("Should have 3 records", 3, read.getCount());

        // Verify first record
        CsvewUserValueDTO firstUser = read.getValues().get(0);
        Assert.assertEquals("john1", firstUser.getUsername());
        Assert.assertEquals("john1@mail.com", firstUser.getEmail());
        Assert.assertEquals("John", firstUser.getFirstname());
        Assert.assertEquals("Doe", firstUser.getLastname());
        Assert.assertNull("ref1 should be null", firstUser.getRef1());
        Assert.assertNull("ref2 should be null", firstUser.getRef2());

        System.out.println("=== Test With Standard Columns ===");
        read.getValues().forEach(System.out::println);
    }

    /**
     * Test case: CSV tanpa header (existing test)
     */
    @Test
    public void testReadWithoutHeader() {
        InputStream is = this.getClass().getResourceAsStream("/csv/user-without-header.csv");
        CsvewResultReader<CsvewUserValueDTO> read = processWithoutHeader(1, is);

        Assert.assertFalse("Should not have errors", read.isError());
        Assert.assertEquals("Should have 9 records", 9, read.getCount());

        System.out.println("=== Test Without Header ===");
        read.getValues().forEach(System.out::println);
    }

    @Override
    public CsvewResultReader<CsvewUserValueDTO> process(int start, InputStream is) {
        CsvewParser csvParseUser = new CsvewParser();
        return readWithoutHeaderValidation(start, is, HEADER, ";",
            (line, columns, validations, value) -> parse(line, columns, validations, value, csvParseUser));
    }

    /**
     * Process CSV without header validation (skips first line)
     */
    public CsvewResultReader<CsvewUserValueDTO> processWithoutHeader(int start, InputStream is) {
        CsvewParser csvParseUser = new CsvewParser();
        return read(start, is, HEADER, ";",
            (line, columns, validations, value) -> parse(line, columns, validations, value, csvParseUser), true);
    }
}
