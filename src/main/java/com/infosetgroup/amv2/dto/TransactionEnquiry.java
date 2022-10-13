package com.infosetgroup.amv2.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TransactionEnquiry {
    private String airtel_money_id;
    private String id;
    private String message;
    private String status;
}
