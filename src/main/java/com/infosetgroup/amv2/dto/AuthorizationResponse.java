package com.infosetgroup.amv2.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthorizationResponse {
    private String access_token;
    private String expires_in;
    private String token_type;
    private String code;
}
