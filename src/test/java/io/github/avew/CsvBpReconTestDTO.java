package io.github.avew;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CsvBpReconTestDTO extends CsvewValue {

    private String taxId;
    private String taxNo;
    private String taxPeriod;
    private String bpStatus;
    private String identityNo;
    private String identityName;
    private String kop;
    private String taxDate;
    private String nitkuPemotong;
    private String lineContent;

}
