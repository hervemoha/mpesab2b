package com.infosetgroup.amv2.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int level;
    private String clientIp;
    private String description;
    private String others;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;


    public Log() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
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

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }


    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
    }
}
