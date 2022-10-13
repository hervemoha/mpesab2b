package com.infosetgroup.amv2.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TransactionRefund {
    private String airtel_money_id;
    private String status;
}
