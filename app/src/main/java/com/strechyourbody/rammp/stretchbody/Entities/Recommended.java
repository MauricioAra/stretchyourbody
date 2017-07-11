package com.strechyourbody.rammp.stretchbody.Entities;

import com.google.gson.annotations.Expose;

/**
 * Created by paulasegura on 11/7/17.
 */

public class Recommended {
    @Expose
    private int id;
    private String name;

    public Recommended(){}
    public Recommended(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
