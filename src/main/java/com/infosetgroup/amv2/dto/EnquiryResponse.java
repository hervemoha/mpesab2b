package com.infosetgroup.amv2.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EnquiryResponse {
    private DataEnquiry data;
    public StatusEnquiry status;
    public String code;
}
