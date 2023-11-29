package io.github.avew;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
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
