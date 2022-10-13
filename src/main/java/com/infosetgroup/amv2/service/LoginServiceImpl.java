package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.Login;
import com.infosetgroup.amv2.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private LoginRepository loginRepository;

    @Autowired
    public LoginServiceImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public Optional<Login> getById(Long id) {
        return loginRepository.findById(id);
    }

    @Override
    public Login getIsEnabled() {
        return null;
    }

    @Override
    public Login saveOrUpdate(Login login) {
        loginRepository.save(login);
        return login;
    }

    @Override
    public List<Login> all() {
        List<Login> logins = new ArrayList<>();
        this.loginRepository.findAll().forEach(logins::add);
        return logins;
    }
}
