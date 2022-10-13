package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.LoginRequest;

public interface LoginRequestService {

    LoginRequest saveOrUpdate(LoginRequest loginRequest);
}
