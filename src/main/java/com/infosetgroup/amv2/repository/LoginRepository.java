package com.infosetgroup.amv2.repository;

import com.infosetgroup.amv2.entity.Login;
import org.springframework.data.repository.CrudRepository;

public interface LoginRepository extends CrudRepository<Login, Long> {
    Login findByName(String name);
}
