package com.infosetgroup.amv2.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponsePaymentCallback {
    private TransactionPaymentCallback transaction;
    private String code;
}
