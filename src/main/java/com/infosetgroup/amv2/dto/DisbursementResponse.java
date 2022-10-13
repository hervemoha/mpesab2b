package com.infosetgroup.amv2.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DisbursementResponse {
    private DataDisbursement data;
    private StatusDisbursement status;
    private String code;
    private String status_message;
    private String status_code;
}
