package com.infosetgroup.amv2.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TransactionPaymentCallback {
    private String id;
    private String message;
    private String status_code;
    private String airtel_money_id;
}
