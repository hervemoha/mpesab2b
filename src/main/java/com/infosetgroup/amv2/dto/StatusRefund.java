package com.infosetgroup.amv2.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StatusRefund {
    private String code;
    private String message;
    private String result_code;
    private boolean success;
}
