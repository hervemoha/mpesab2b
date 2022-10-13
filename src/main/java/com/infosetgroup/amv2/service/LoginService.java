package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.Login;

import java.util.List;
import java.util.Optional;

public interface LoginService {

    Optional<Login> getById(Long id);
    Login getIsEnabled();
    Login saveOrUpdate(Login login);
    List<Login> all();
}
