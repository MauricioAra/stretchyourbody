package com.strechyourbody.rammp.stretchbody.Entities;

import java.util.Date;
import java.util.Set;
/**
 * Created by Mathias on 7/17/17.
 */

public class UserRegister {

    private Long id;

    private String login;

    private String email;

    private String password;

    public UserRegister() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
