package com.strechyourbody.rammp.stretchbody.Entities;

/**
 * Created by Mathias on 7/15/17.
 */

public class UserSession {

    private String token;
    private Long userId;
    private String username;

    public UserSession() {
    }

    public UserSession(String token, Long userId, String username) {
        this.token = token;
        this.userId = userId;
        this.username = username;
    }

    public UserSession(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {

        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "token=" + token +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }
}
