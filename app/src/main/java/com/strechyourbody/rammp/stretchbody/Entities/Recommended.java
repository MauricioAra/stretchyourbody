package com.strechyourbody.rammp.stretchbody.Entities;

import com.google.gson.annotations.Expose;

/**
 * Created by paulasegura on 11/7/17.
 */

public class Recommended {
    @Expose
    private int id;
    private String name;
    private String tag;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setName(String name) {
        this.name = name;
    }
}
