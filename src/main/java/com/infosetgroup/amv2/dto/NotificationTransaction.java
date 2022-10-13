package com.infosetgroup.amv2.dto;

public class NotificationTransaction {
    private String id;
    private String message;
    private String status_code;
    private String airtel_money_id;

    public NotificationTransaction() {
    }

    public NotificationTransaction(String id, String message, String status_code, String airtel_money_id) {
        this.id = id;
        this.message = message;
        this.status_code = status_code;
        this.airtel_money_id = airtel_money_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getAirtel_money_id() {
        return airtel_money_id;
    }

    public void setAirtel_money_id(String airtel_money_id) {
        this.airtel_money_id = airtel_money_id;
    }
}
