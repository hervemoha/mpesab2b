package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.NotificationAi;
import com.infosetgroup.amv2.repository.NotificationAiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationAiServiceImpl implements NotificationAiService {

    @Autowired
    private NotificationAiRepository repository;

    @Override
    public NotificationAi saveOrUpdate(NotificationAi notification) {
        this.repository.save(notification);
        return notification;
    }
}
