package com.strechyourbody.rammp.stretchbody.Entities;

/**
 * Created by dev on 8/16/17.
 */

public class BodyPoint {


    private Long id;

    private String bodypart;

    private Long idbodypart;

    private Long userAppId;

    private String userAppName;



    public BodyPoint(){}

    public BodyPoint(Long id, String bodypart, Long idbodypart, Long userAppId, String userAppName) {
        this.id = id;
        this.bodypart = bodypart;
        this.idbodypart = idbodypart;
        this.userAppId = userAppId;
        this.userAppName = userAppName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBodypart() {
        return bodypart;
    }

    public void setBodypart(String bodypart) {
        this.bodypart = bodypart;
    }

    public Long getIdbodypart() {
        return idbodypart;
    }

    public void setIdbodypart(Long idbodypart) {
        this.idbodypart = idbodypart;
    }

    public Long getUserAppId() {
        return userAppId;
    }

    public void setUserAppId(Long userAppId) {
        this.userAppId = userAppId;
    }

    public String getUserAppName() {
        return userAppName;
    }

    public void setUserAppName(String userAppName) {
        this.userAppName = userAppName;
    }
}
