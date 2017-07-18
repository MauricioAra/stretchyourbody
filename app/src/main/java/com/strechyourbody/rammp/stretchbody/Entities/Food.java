package com.strechyourbody.rammp.stretchbody.Entities;

import com.google.gson.annotations.Expose;

/**
 * Created by paulasegura on 16/7/17.
 */

public class Food {
    @Expose
    private Long id;

    private String name;

    private String image;

    private String description;

    private Boolean status;

    private Boolean isRecommended;

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

    public Food(Long id, String name, String image, String description, Boolean status, Boolean isRecommended) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.status = status;
        this.isRecommended = isRecommended;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getRecommended() {
        return isRecommended;
    }

    public void setRecommended(Boolean recommended) {
        isRecommended = recommended;
    }
}
