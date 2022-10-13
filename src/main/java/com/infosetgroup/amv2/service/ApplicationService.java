package com.infosetgroup.amv2.service;


import com.infosetgroup.amv2.entity.Application;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    List<Application> listAll();
    Optional<Application> getById(Long id);
    Application getByCodeAndPassword(String code, String password);
    Application saveOrUpdate(Application application);
}
