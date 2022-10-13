package com.infosetgroup.amv2.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TransactionAiRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subsmsisdn;
    private String login;
    private String password;
    private String mermsisdn;
    private String callBackDestination;

    @Column(length = 1024)
    private String description;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;

    @OneToOne
    @JoinColumn
    private TransactionAi transactionAi;

    @OneToOne(mappedBy = "transactionAiRequest")
    private TransactionAiResponse transactionAiResponse;

    public TransactionAiRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSubsmsisdn() {
        return subsmsisdn;
    }

    public void setSubsmsisdn(String subsmsisdn) {
        this.subsmsisdn = subsmsisdn;
    }

    public String getMermsisdn() {
        return mermsisdn;
    }

    public void setMermsisdn(String mermsisdn) {
        this.mermsisdn = mermsisdn;
    }

    public String getCallBackDestination() {
        return callBackDestination;
    }

    public void setCallBackDestination(String callBackDestination) {
        this.callBackDestination = callBackDestination;
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

    public TransactionAi getTransactionAi() {
        return transactionAi;
    }

    public void setTransactionAi(TransactionAi transactionAi) {
        this.transactionAi = transactionAi;
    }

    public TransactionAiResponse getTransactionAiResponse() {
        return transactionAiResponse;
    }

    public void setTransactionAiResponse(TransactionAiResponse transactionAiResponse) {
        this.transactionAiResponse = transactionAiResponse;
    }

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
    }
}
