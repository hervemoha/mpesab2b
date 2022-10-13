package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.TransactionAiResponse;
import com.infosetgroup.amv2.repository.TransactionAiResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionAiResponseServiceImpl implements TransactionAiResponseService {

    @Autowired
    private TransactionAiResponseRepository repository;

    @Override
    public TransactionAiResponse saveOrUpdate(TransactionAiResponse SalesResponse) {
        this.repository.save(SalesResponse);
        return SalesResponse;
    }
}
