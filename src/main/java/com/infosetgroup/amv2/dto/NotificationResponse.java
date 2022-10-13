package com.infosetgroup.amv2.dto;

public class NotificationResponse {
    private NotificationTransaction transaction;

    public NotificationResponse() {
    }

    public NotificationResponse(NotificationTransaction transaction) {
        this.transaction = transaction;
    }

    public NotificationTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(NotificationTransaction transaction) {
        this.transaction = transaction;
    }
}
