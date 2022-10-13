package com.infosetgroup.amv2.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MerchantAiApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;

    @ManyToOne
    private MerchantAi merchant;

    @ManyToOne
    private Application application;

    public MerchantAiApplication() {
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

    public MerchantAi getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantAi merchant) {
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
