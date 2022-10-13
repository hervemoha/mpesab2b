package com.infosetgroup.amv2.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RefundResponse {
    private DataRefund data;
    private StatusRefund status;
    private String code;
}
