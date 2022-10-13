package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.Log;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface LogService {

    Log saveOrUpdate(Log log);
    List<Log> all();
    Optional<Log> getById(Long id);
    Page<Log> getLogsByPage(int p);
}
