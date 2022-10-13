package com.infosetgroup.amv2.entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MerchantNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String resultCode;
    private String resultDesc;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;

    @OneToOne
    @JoinColumn
    private Notification notification;

    public MerchantNotification() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
    }
}
