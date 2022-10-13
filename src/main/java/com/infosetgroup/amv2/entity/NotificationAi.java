package com.infosetgroup.amv2.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class NotificationAi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transId;
    private String systemid;
    private String resultCode;
    private String resultDesc;

    @Column(length = 1024)
    private String description;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;

    @OneToOne
    private TransactionAi transactionAi;

    @OneToOne(mappedBy = "notification")
    private MerchantNotificationAi merchantNotification;

    private String hash;

    public NotificationAi() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public TransactionAi getTransactionAi() {
        return transactionAi;
    }

    public void setTransactionAi(TransactionAi transactionAi) {
        this.transactionAi = transactionAi;
    }

    public MerchantNotificationAi getMerchantNotification() {
        return merchantNotification;
    }

    public void setMerchantNotification(MerchantNotificationAi merchantNotification) {
        this.merchantNotification = merchantNotification;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
    }
}
