package com.strechyourbody.rammp.stretchbody.Entities;

/**
 * Created by mbp on 7/16/17.
 */

public class Exercise {

    private Long id;

    private String name;

    private String image;

    private String time;

    private Integer repetition;

    private String difficulty;

    private Integer calification;

    private Boolean isSelected = false;

    private Long bodyPartId;

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public Exercise(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getRepetition() {
        return repetition;
    }

    public void setRepetition(Integer repetition) {
        this.repetition = repetition;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getCalification() {
        return calification;
    }

    public void setCalification(Integer calification) {
        this.calification = calification;
    }

    public Long getBodyPartId() { return bodyPartId; }

    public void setBodyPartId(Long bodyPartId) { this.bodyPartId = bodyPartId; }

}
