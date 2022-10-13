package com.infosetgroup.amv2.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StatusDisbursement {
    private String code;
    private String message;
    private String result_code;
    private String response_code;
    private boolean success;
}
