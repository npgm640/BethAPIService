package com.beth.infy.domain;

import com.beth.infy.util.CommonConstants;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class AuthResponse extends CommonResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String jwtToken;


    public AuthResponse(String jwtToken) {
        super(CommonConstants.SUCCESS, HttpStatus.OK.value(), CommonConstants.TOKEN_CREATED);
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return this.jwtToken;
    }
}
