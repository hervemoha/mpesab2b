package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.LoginResponse;
import com.infosetgroup.amv2.repository.LoginResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginResponseServiceImpl implements LoginResponseService{

    private LoginResponseRepository loginResponseRepository;

    @Autowired
    public LoginResponseServiceImpl(LoginResponseRepository loginResponseRepository) {
        this.loginResponseRepository = loginResponseRepository;
    }

    @Override
    public LoginResponse saveOrUpdate(LoginResponse loginResponse) {
        loginResponseRepository.save(loginResponse);
        return loginResponse;
    }
}
