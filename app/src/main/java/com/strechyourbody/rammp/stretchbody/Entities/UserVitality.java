package com.strechyourbody.rammp.stretchbody.Entities;

/**
 * Created by dev on 7/30/17.
 */

public class UserVitality {

    private Long id;
    private Integer range;
    private String comment;
    private Long userAppId;

    public UserVitality(){}

    public Long getID() {
        return id;
    }

    public Integer getRange() {
        return range;
    }

    public String getComment() {
        return comment;
    }

    public Long getUserAppID() {
        return userAppId;
    }


    public void setID(Long ID) {
        this.id = ID;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUserAppID(Long userAppID) {
        this.userAppId = userAppID;
    }

}
