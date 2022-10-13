package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.MerchantNotification;
import com.infosetgroup.amv2.repository.MerchantNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantNotificationServiceImpl implements MerchantNotificationService {

    private MerchantNotificationRepository merchantNotificationRepository;

    @Autowired
    public MerchantNotificationServiceImpl(MerchantNotificationRepository merchantNotificationRepository) {
        this.merchantNotificationRepository = merchantNotificationRepository;
    }

    @Override
    public MerchantNotification saveOrUpdate(MerchantNotification merchantNotification) {
        merchantNotificationRepository.save(merchantNotification);
        return merchantNotification;
    }
}
