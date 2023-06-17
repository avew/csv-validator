package io.github.avew;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CsvSftpValue extends CsvewValue {

    private String identityId;
    private String identityType;
    private String identityName;
    private String docNo;
    private String docDate;
    private String docTemplate;
    private String docKopur;
    private String docPassword;
    private String docReference;
}
