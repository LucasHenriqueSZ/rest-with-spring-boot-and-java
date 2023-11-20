package com.rest.api.data.dto.v1.security;

import java.io.Serializable;

public class AccountCredentialsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;

    public AccountCredentialsDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
}
