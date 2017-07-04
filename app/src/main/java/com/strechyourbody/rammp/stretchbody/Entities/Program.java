package com.strechyourbody.rammp.stretchbody.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mbp on 7/1/17.
 */

public class Program {

    @Expose
    private int id;
    private String name;

    public Program(){}
    public Program(int id, String name) {
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
