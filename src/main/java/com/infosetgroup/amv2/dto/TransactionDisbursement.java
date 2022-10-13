package com.infosetgroup.amv2.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TransactionDisbursement {
    private String reference_id;
    private String airtel_money_id;
    private String id;
}
