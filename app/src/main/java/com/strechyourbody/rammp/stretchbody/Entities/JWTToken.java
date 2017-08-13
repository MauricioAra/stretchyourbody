package com.strechyourbody.rammp.stretchbody.Entities;

/**
 * Created by Mathias on 7/10/17.
 */

public class JWTToken {

    private String id_token;

    public JWTToken(String idToken) {
        this.id_token = idToken;
    }

    public String getIdToken() {
        return id_token;
    }

    public void setIdToken(String idToken) {
        this.id_token = idToken;
    }
}
