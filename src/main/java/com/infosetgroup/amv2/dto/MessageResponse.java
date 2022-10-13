package com.infosetgroup.amv2.dto;

public class MessageResponse {
    private String code;
    private String message;
    private String url;
    private String orderNumber;

    public MessageResponse() {
    }

    public MessageResponse(String code, String message, String url, String orderNumber) {
        this.code = code;
        this.message = message;
        this.url = url;
        this.orderNumber = orderNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
