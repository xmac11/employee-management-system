package com.team.ghana.authJwt;

import java.io.Serializable;

public class JwtResponsePJ implements Serializable {

    private static final long serialVersionUID = 0x2c98011fL;

    private final String jwtoken;

    public JwtResponsePJ(String jwttoken) {
        this.jwtoken = jwttoken;
    }

    public String getJWToken() {
        return jwtoken;
    }
}
