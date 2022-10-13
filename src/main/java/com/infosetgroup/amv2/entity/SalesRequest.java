package com.infosetgroup.amv2.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SalesRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeRequest;
    private String shortCode;
    private String commandId;
    private String callBackChannel;
    private String callBackDestination;

    @Column(name = "description")
    @Lob
    private String description;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;

    @OneToOne
    @JoinColumn
    private Sales sale;

    @OneToOne(mappedBy = "salesRequest")
    private SalesResponse salesResponse;

    public SalesRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeRequest() {
        return codeRequest;
    }

    public void setCodeRequest(String codeRequest) {
        this.codeRequest = codeRequest;
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

    public String getCallBackChannel() {
        return callBackChannel;
    }

    public void setCallBackChannel(String callBackChannel) {
        this.callBackChannel = callBackChannel;
    }

    public String getCallBackDestination() {
        return callBackDestination;
    }

    public void setCallBackDestination(String callBackDestination) {
        this.callBackDestination = callBackDestination;
    }

    public Sales getSale() {
        return sale;
    }

    public void setSale(Sales sale) {
        this.sale = sale;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public SalesResponse getSalesResponse() {
        return salesResponse;
    }

    public void setSalesResponse(SalesResponse salesResponse) {
        this.salesResponse = salesResponse;
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
