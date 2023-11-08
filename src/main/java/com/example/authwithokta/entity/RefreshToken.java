package com.example.authwithokta.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Entity
public class RefreshToken {
    @Id
    private String refreshToken;
    private Date issuedAt;
    private Date expiry;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
