package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.SalesRequest;
import com.infosetgroup.amv2.repository.SalesRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesRequestServiceImpl implements SalesRequestService {

    private SalesRequestRepository salesRequestRepository;

    @Autowired
    public SalesRequestServiceImpl(SalesRequestRepository salesRequestRepository) {
        this.salesRequestRepository = salesRequestRepository;
    }

    @Override
    public SalesRequest saveOrUpdate(SalesRequest salesRequest) {
        salesRequestRepository.save(salesRequest);
        return salesRequest;
    }
}
