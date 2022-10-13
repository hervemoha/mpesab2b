package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.Configuration;

import java.util.List;
import java.util.Optional;

public interface ConfigurationService {
    List<Configuration> all();
    Optional<Configuration> getById(Long id);
    Configuration findByEnabled();
    Configuration saveOrUpdate(Configuration configuration);
}
