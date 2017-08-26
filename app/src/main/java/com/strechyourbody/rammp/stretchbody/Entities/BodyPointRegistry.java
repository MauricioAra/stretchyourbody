package com.strechyourbody.rammp.stretchbody.Entities;

/**
 * Created by dev on 8/20/17.
 */

public class BodyPointRegistry {
    private Long ID;
    private String type;
    private String comment;
    private Long bodyPointId;
    private String bodyPointBodypart;

    public BodyPointRegistry(){};

    public BodyPointRegistry(Long ID, String type, String comment, Long bodyPointId, String bodyPointBodypart) {
        this.ID = ID;
        this.type = type;
        this.comment = comment;
        this.bodyPointId = bodyPointId;
        this.bodyPointBodypart = bodyPointBodypart;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getBodyPointId() {
        return bodyPointId;
    }

    public void setBodyPointId(Long bodyPointId) {
        this.bodyPointId = bodyPointId;
    }

    public String getBodyPointBodypart() {
        return bodyPointBodypart;
    }

    public void setBodyPointBodypart(String bodyPointBodypart) {
        this.bodyPointBodypart = bodyPointBodypart;
    }
}
