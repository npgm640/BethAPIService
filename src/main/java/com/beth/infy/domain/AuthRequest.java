package com.beth.infy.domain;

import com.beth.infy.common.validation.NotNull;

import java.io.Serializable;

public class AuthRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message="userId cannot be null")
    private String userName;

    @NotNull(message="password cannot be null")
    private String password;

    public AuthRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
