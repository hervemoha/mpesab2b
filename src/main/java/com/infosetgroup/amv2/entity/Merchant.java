package com.infosetgroup.amv2.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private String shortCode;
    private String commandId;
    private boolean enabled = true;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;
    private LocalDateTime modified;

    private int type;


    @OneToMany(mappedBy = "merchant")
    private List<MerchantApplication> merchantApplications;

    @ManyToOne
    private Login login;


    public Merchant() {
        this.merchantApplications = new ArrayList<>();
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

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
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


    public List<MerchantApplication> getMerchantApplications() {
        return merchantApplications;
    }

    public void setMerchantApplications(List<MerchantApplication> merchantApplications) {
        this.merchantApplications = merchantApplications;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
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
}
