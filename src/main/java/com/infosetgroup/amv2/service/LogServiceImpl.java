package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.Log;
import com.infosetgroup.amv2.repository.LogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LogServiceImpl implements LogService {

    private LogRepository logRepository;

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public Log saveOrUpdate(Log log) {
        logRepository.save(log);
        return log;
    }

    @Override
    public List<Log> all() {
        List<Log> logs = new ArrayList<>();
        logRepository.findAll().forEach(logs::add);
        return logs;
    }

    @Override
    public Optional<Log> getById(Long id) {
        return logRepository.findById(id);
    }

    @Override
    public Page<Log> getLogsByPage(int p) {
        Page<Log> logs = logRepository.findAll(PageRequest.of(p, 20));
        return logs;
    }
}
