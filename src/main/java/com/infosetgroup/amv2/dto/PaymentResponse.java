package com.infosetgroup.amv2.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentResponse {
    private DataPayment data;
    private StatusPayment status;
    private String code;
    private String status_message;
    private String status_code;
}
