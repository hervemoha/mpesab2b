package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.Configuration;
import com.infosetgroup.amv2.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    private ConfigurationRepository configurationRepository;

    @Autowired
    public ConfigurationServiceImpl(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    public List<Configuration> all() {
        List<Configuration> configurations = new ArrayList<>();
        this.configurationRepository.findAll().forEach(configurations::add);
        return configurations;
    }

    @Override
    public Optional<Configuration> getById(Long id) {
        return configurationRepository.findById(id);
    }

    @Override
    public Configuration findByEnabled() {
        return configurationRepository.findByEnabled(true);
    }

    @Override
    public Configuration saveOrUpdate(Configuration configuration) {
        configurationRepository.save(configuration);
        return configuration;
    }
}
