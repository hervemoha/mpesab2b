package com.infosetgroup.amv2.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MerchantAi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
    private String login;
    private String password;
    private String mermsisdn;
    private boolean enabled = true;
    private String interfaceId;
    private String pin;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;
    private LocalDateTime modified;

    private int type;

    @OneToMany(mappedBy = "merchant")
    private List<MerchantAiApplication> merchantAiApplications = new ArrayList<>();

    private String authorization;

    @Column(length = 10)
    private String country;

    public MerchantAi() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMermsisdn() {
        return mermsisdn;
    }

    public void setMermsisdn(String mermsisdn) {
        this.mermsisdn = mermsisdn;
    }

    public List<MerchantAiApplication> getMerchantAiApplications() {
        return merchantAiApplications;
    }

    public void setMerchantAiApplications(List<MerchantAiApplication> merchantAiApplications) {
        this.merchantAiApplications = merchantAiApplications;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
        this.setModified(LocalDateTime.now());
    }
}
