package com.infosetgroup.amv2.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean completed = false;
    private String codeValue;
    private String code;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;
    private LocalDateTime modified;

    private String customerMsisdn;
    private String currency;
    private String amount;
    private String reference;
    private String language;
    private String loginApp;
    private String merchantName;
    private String description;
    private String callBackUrl;
    private String passwordApp;
    private String shortCode;
    private String providerId;

    private int type;

    @ManyToOne
    private Merchant merchant;

    @ManyToOne
    private Application application;

    @OneToOne(mappedBy = "sale", cascade = CascadeType.ALL)
    private LoginRequest loginRequest;

    @OneToOne(mappedBy = "sale", cascade = CascadeType.ALL)
    private LoginResponse loginResponse;

    @OneToOne(mappedBy = "sale", cascade = CascadeType.ALL)
    private SalesRequest salesRequest;

    @OneToOne(mappedBy = "sale", cascade = CascadeType.ALL)
    private SalesResponse salesResponse;

    @OneToOne(mappedBy = "sale", cascade = CascadeType.ALL)
    private MerchantSalesResponse merchantSalesResponse;

    @OneToOne(mappedBy = "sale", cascade = CascadeType.ALL)
    private Notification notification;

    private String errorDescription;

    public Sales() {
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

    public String getCustomerMsisdn() {
        return customerMsisdn;
    }

    public void setCustomerMsisdn(String customerMsisdn) {
        this.customerMsisdn = customerMsisdn;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLoginApp() {
        return loginApp;
    }

    public void setLoginApp(String loginApp) {
        this.loginApp = loginApp;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public String getPasswordApp() {
        return passwordApp;
    }

    public void setPasswordApp(String passwordApp) {
        this.passwordApp = passwordApp;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
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

    public LoginRequest getLoginRequest() {
        return loginRequest;
    }

    public void setLoginRequest(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    public SalesRequest getSalesRequest() {
        return salesRequest;
    }

    public void setSalesRequest(SalesRequest salesRequest) {
        this.salesRequest = salesRequest;
    }

    public SalesResponse getSalesResponse() {
        return salesResponse;
    }

    public void setSalesResponse(SalesResponse salesResponse) {
        this.salesResponse = salesResponse;
    }

    public MerchantSalesResponse getMerchantSalesResponse() {
        return merchantSalesResponse;
    }

    public void setMerchantSalesResponse(MerchantSalesResponse merchantSalesResponse) {
        this.merchantSalesResponse = merchantSalesResponse;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
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

    public String createdDateToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return this.created.format(formatter);
    }
}
