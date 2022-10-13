package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.LoginRequest;
import com.infosetgroup.amv2.repository.LoginRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginRequestServiceImpl implements LoginRequestService {

    private LoginRequestRepository loginRequestRepository;

    @Autowired
    public LoginRequestServiceImpl(LoginRequestRepository loginRequestRepository) {
        this.loginRequestRepository = loginRequestRepository;
    }

    @Override
    public LoginRequest saveOrUpdate(LoginRequest loginRequest) {
        loginRequestRepository.save(loginRequest);
        return loginRequest;
    }
}
