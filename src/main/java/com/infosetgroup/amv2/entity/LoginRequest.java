package com.infosetgroup.amv2.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LoginRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @Column(name = "description", length = 500)
    private String description;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;

    @OneToOne
    private Sales sale;

    @OneToOne(mappedBy = "loginRequest")
    private LoginResponse loginResponse;

    public LoginRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Sales getSale() {
        return sale;
    }

    public void setSale(Sales sale) {
        this.sale = sale;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
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
