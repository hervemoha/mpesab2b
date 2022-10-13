package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.TransactionAiRequest;
import com.infosetgroup.amv2.repository.TransactionAiRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionAiRequestServiceImpl implements TransactionAiRequestService {

    @Autowired
    private TransactionAiRequestRepository repository;

    @Override
    public TransactionAiRequest saveOrUpdate(TransactionAiRequest salesRequest) {
        this.repository.save(salesRequest);
        return salesRequest;
    }
}
