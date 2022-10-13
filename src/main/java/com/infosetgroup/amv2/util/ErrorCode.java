package com.infosetgroup.amv2.util;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ErrorCode {
    private String code;
    private String reason;
    private String description;
}
