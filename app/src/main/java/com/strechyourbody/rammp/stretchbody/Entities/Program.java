package com.strechyourbody.rammp.stretchbody.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mbp on 7/1/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Program {

    //@SerializedName("_id")
    //@Expose

    private String id;

    private String name;

    private String intDate;

    private String finishDate;

    private Integer interval;

    private Integer cantRepetition;

    private Boolean status;

    private Boolean isDairy;

    private Boolean isRecommended;

    private Long userAppId;

    private List<Exercise> exercises;

    public Program(){}

    public Program(String id, String name, String intDate, String finishDate, Integer interval, Integer cantRepetition, Boolean status, Boolean isDairy, Boolean isRecommended) {
        this.id = id;
        this.name = name;
        this.intDate = intDate;
        this.finishDate = finishDate;
        this.interval = interval;
        this.cantRepetition = cantRepetition;
        this.status = status;
        this.isDairy = isDairy;
        this.isRecommended = isRecommended;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getCantRepetition() {
        return cantRepetition;
    }

    public Long getUserAppId() {
        return userAppId;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void setUserAppId(Long userAppId) {
        this.userAppId = userAppId;
    }

    public void setCantRepetition(Integer cantRepetition) {
        this.cantRepetition = cantRepetition;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getDairy() {
        return isDairy;
    }

    public void setDairy(Boolean dairy) {
        isDairy = dairy;
    }

    public Boolean getRecommended() {
        return isRecommended;
    }

    public void setRecommended(Boolean recommended) {
        isRecommended = recommended;
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