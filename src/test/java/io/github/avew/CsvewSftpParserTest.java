package io.github.avew;

import io.github.avew.reader.CsvewReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Collection;

public class CsvewSftpParserTest extends CsvewReader<CsvSftpValue> {

    static String[] HEADER = {
            "Identity Number",
            "Identity Type",
            "Identity Name",
            "Document Number",
            "Document Date",
            "Template Code",
            "Transaction Value",
            "Password",
            "Document Reference",
    };

    @Test
    public void testReadSuccess() {
        InputStream is = this.getClass().getResourceAsStream("/csv/1.csv");
        CsvewResultReader<CsvSftpValue> read = process(is);
        System.out.println(read.getValues().toString());
    }


    @Override
    public CsvewResultReader<CsvSftpValue> process(InputStream is) {
        CsvewParser csvParseUser = new CsvewParser();
        return read(
                is,
                HEADER,
                ";",
                (int line, String[] columns, Collection<CsvewValidationDTO> validations, CsvSftpValue value) -> {
                    csvParseUser.parseString(line, 0, HEADER[0], columns[0], true, validations, value::setIdentityId);
                    csvParseUser.parseString(line, 1, HEADER[1], columns[1], true, validations, value::setIdentityType);
                    csvParseUser.parseString(line, 2, HEADER[2], columns[2], true, validations, value::setIdentityName);
                    csvParseUser.parseString(line, 3, HEADER[3], columns[3], true, validations, value::setDocNo);
                    csvParseUser.parseString(line, 4, HEADER[4], columns[4], true, validations, value::setDocDate);
                    csvParseUser.parseString(line, 5, HEADER[5], columns[5], true, validations, value::setDocTemplate);
                    csvParseUser.parseString(line, 6, HEADER[6], columns[6], false, validations, value::setDocKopur);

                    try {
                        csvParseUser.parseString(line, 7, HEADER[7], columns[7], false, validations, value::setDocPassword);
                        csvParseUser.parseString(line, 8, HEADER[8], columns[8], false, validations, value::setDocReference);
                    } catch (IndexOutOfBoundsException ignore) {
                    }
                });
    }


}
