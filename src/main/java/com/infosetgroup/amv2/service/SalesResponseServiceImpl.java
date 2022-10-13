package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.SalesResponse;
import com.infosetgroup.amv2.repository.SalesResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesResponseServiceImpl implements SalesResponseService {

    private SalesResponseRepository salesResponseRepository;

    @Autowired
    public void setSalesResponseRepository(SalesResponseRepository salesResponseRepository) {
        this.salesResponseRepository = salesResponseRepository;
    }

    @Override
    public SalesResponse saveOrUpdate(SalesResponse SalesResponse) {
        salesResponseRepository.save(SalesResponse);
        return SalesResponse;
    }
}
