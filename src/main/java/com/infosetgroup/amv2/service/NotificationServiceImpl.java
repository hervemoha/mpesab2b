package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.Notification;
import com.infosetgroup.amv2.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification saveOrUpdate(Notification notification) {
        notificationRepository.save(notification);
        return notification;
    }
}
