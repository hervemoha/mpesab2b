package com.infosetgroup.amv2.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
    private String login;

    private boolean enabled = true;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;
    private LocalDateTime modified;

    private int type;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<MerchantAiApplication> merchantAiApplications;


    public Application() {
        this.merchantAiApplications = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
        this.setModified(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate() {
        this.setModified(LocalDateTime.now());
    }


    public List<MerchantAiApplication> getMerchantAiApplications() {
        return merchantAiApplications;
    }

    public void setMerchantAiApplications(List<MerchantAiApplication> merchantAiApplications) {
        this.merchantAiApplications = merchantAiApplications;
    }

}
