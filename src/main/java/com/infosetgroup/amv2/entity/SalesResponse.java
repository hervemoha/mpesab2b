package com.infosetgroup.amv2.entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SalesResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String description;
    private String detail;
    private String transactionId;
    private String insightReference;

    @Column(name = "description_response")
    @Lob
    private String descriptionResponse;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;

    @OneToOne
    @JoinColumn
    private SalesRequest salesRequest;

    @OneToOne
    @JoinColumn
    private Sales sale;

    public SalesResponse() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public SalesRequest getSalesRequest() {
        return salesRequest;
    }

    public void setSalesRequest(SalesRequest salesRequest) {
        this.salesRequest = salesRequest;
    }

    public Sales getSale() {
        return sale;
    }

    public void setSale(Sales sale) {
        this.sale = sale;
    }

    public String getDescriptionResponse() {
        return descriptionResponse;
    }

    public void setDescriptionResponse(String descriptionResponse) {
        this.descriptionResponse = descriptionResponse;
    }

    public String getInsightReference() {
        return insightReference;
    }

    public void setInsightReference(String insightReference) {
        this.insightReference = insightReference;
    }

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
    }
}
