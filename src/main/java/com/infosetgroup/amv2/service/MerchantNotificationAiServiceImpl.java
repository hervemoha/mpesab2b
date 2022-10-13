package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.MerchantNotificationAi;
import com.infosetgroup.amv2.repository.MerchantNotificationAiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantNotificationAiServiceImpl implements MerchantNotificationAiService {

    @Autowired
    private MerchantNotificationAiRepository repository;

    @Override
    public MerchantNotificationAi saveOrUpdate(MerchantNotificationAi merchantNotification) {
        this.repository.save(merchantNotification);
        return merchantNotification;
    }
}
