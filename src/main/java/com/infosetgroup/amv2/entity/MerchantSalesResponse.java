package com.infosetgroup.amv2.entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MerchantSalesResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String message;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;

    @OneToOne
    private SalesResponse salesResponse;

    @OneToOne
    @JoinColumn
    private Sales sale;

    public MerchantSalesResponse() {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Sales getSale() {
        return sale;
    }

    public void setSale(Sales sale) {
        this.sale = sale;
    }

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
    }
}
