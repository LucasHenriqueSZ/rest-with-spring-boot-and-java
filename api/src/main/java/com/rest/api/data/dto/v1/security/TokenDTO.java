package com.rest.api.data.dto.v1.security;

import java.io.Serializable;
import java.util.Date;

public class TokenDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private Boolean authenticated;
    private Date created;
    private Date expiration;
    private String acessToken;
    private String refreshToken;

    public TokenDTO() {
    }

    public TokenDTO(String username, Boolean authenticated, Date created, Date expiration, String acessToken, String refreshToken) {
        this.username = username;
        this.authenticated = authenticated;
        this.created = created;
        this.expiration = expiration;
        this.acessToken = acessToken;
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getAcessToken() {
        return acessToken;
    }

    public void setAcessToken(String acessToken) {
        this.acessToken = acessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
