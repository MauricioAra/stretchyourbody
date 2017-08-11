package com.strechyourbody.rammp.stretchbody.Entities;

/**
 * Created by paulasegura on 10/8/17.
 */

public class FoodTag {

    private int id;
    private String name;

    public FoodTag(int id, String name) {
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
