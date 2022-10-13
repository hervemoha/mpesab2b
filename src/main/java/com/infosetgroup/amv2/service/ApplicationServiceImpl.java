package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.Application;
import com.infosetgroup.amv2.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public List<Application> listAll() {
        List<Application> applications = new ArrayList<>();
        this.applicationRepository.findAll().forEach(applications::add);
        return applications;
    }

    @Override
    public Optional<Application> getById(Long id) {
        return this.applicationRepository.findById(id);
    }

    @Override
    public Application getByCodeAndPassword(String code, String password) {
        return this.applicationRepository.findByCodeAndLogin(code, password);
    }

    @Override
    public Application saveOrUpdate(Application application) {
        this.applicationRepository.save(application);
        return application;
    }
}
