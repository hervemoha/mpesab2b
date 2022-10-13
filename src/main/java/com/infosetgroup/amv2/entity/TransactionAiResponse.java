package com.infosetgroup.amv2.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TransactionAiResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private String message;
    private String tnxId;
    private String trId;
    private String resultCode;
    private String responseCode;
    private String code;

    @Column(length = 1024)
    private String descriptionResponse;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;

    @OneToOne
    @JoinColumn
    private TransactionAiRequest transactionAiRequest;

    @OneToOne
    @JoinColumn
    private TransactionAi transactionAi;

    public TransactionAiResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescriptionResponse() {
        return descriptionResponse;
    }

    public void setDescriptionResponse(String descriptionResponse) {
        this.descriptionResponse = descriptionResponse;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public TransactionAiRequest getTransactionAiRequest() {
        return transactionAiRequest;
    }

    public void setTransactionAiRequest(TransactionAiRequest transactionAiRequest) {
        this.transactionAiRequest = transactionAiRequest;
    }

    public TransactionAi getTransactionAi() {
        return transactionAi;
    }

    public void setTransactionAi(TransactionAi transactionAi) {
        this.transactionAi = transactionAi;
    }

    public String getTnxId() {
        return tnxId;
    }

    public void setTnxId(String tnxId) {
        this.tnxId = tnxId;
    }

    public String getTrId() {
        return trId;
    }

    public void setTrId(String trId) {
        this.trId = trId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
    }
}
