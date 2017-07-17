package com.strechyourbody.rammp.stretchbody.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mbp on 7/1/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Program {

    //@SerializedName("_id")
    //@Expose

    private String id;

    private String name;

    private String intDate;

    private String finishDate;

    public Program(){}

    public Program(String id, String name, String intDate, String finishDate) {
        this.id = id;
        this.name = name;
        this.intDate = intDate;
        this.finishDate = finishDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntDate() {
        return intDate;
    }

    public void setIntDate(String intDate) {
        this.intDate = intDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }
}