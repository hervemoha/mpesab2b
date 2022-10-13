package com.infosetgroup.amv2.repository;

import com.infosetgroup.amv2.entity.Configuration;
import org.springframework.data.repository.CrudRepository;

public interface ConfigurationRepository extends CrudRepository<Configuration, Long> {
    Configuration findByEnabled(boolean b);
}
