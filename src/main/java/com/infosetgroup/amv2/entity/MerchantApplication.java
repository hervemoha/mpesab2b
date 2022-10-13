package com.infosetgroup.amv2.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MerchantApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;

    @ManyToOne
    private Merchant merchant;

    @ManyToOne
    private Application application;


    public MerchantApplication() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }


    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
    }
}
