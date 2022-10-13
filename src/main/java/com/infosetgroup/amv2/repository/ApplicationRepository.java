package com.infosetgroup.amv2.repository;

import com.infosetgroup.amv2.entity.Application;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationRepository extends CrudRepository<Application, Long> {
    Application findByCodeAndLogin(String code, String password);
}
