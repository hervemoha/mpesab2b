package com.infosetgroup.amv2.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;
    private String resultType;
    private String resultCode;
    private String resultDesc;
    private String transactionId;
    private String insightReference;

    @Column(name = "description", length = 500)
    private String description;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;

    @OneToOne
    private Sales sale;

    @OneToOne(mappedBy = "notification")
    private MerchantNotification merchantNotification;

    public Notification() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getInsightReference() {
        return insightReference;
    }

    public void setInsightReference(String insightReference) {
        this.insightReference = insightReference;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Sales getSale() {
        return sale;
    }

    public MerchantNotification getMerchantNotification() {
        return merchantNotification;
    }

    public void setMerchantNotification(MerchantNotification merchantNotification) {
        this.merchantNotification = merchantNotification;
    }

    public void setSale(Sales sale) {
        this.sale = sale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
    }
}
