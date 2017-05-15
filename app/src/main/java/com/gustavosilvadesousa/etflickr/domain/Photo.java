package com.gustavosilvadesousa.etflickr.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Photo implements Serializable {

    private String id;
    private String owner;
    private String secret;
    private String farm;
    private String title;
    private String server;
    @JsonProperty("ispublic")
    private int isPublic;
    @JsonProperty("isfamily")
    private int isFamily;
    @JsonProperty("isfriend")
    private int isFriend;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public int getIsFamily() {
        return isFamily;
    }

    public void setIsFamily(int isFamily) {
        this.isFamily = isFamily;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public String getUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append("https://farm");
        builder.append(farm);
        builder.append(".staticflickr.com/");
        builder.append(server);
        builder.append("/");
        builder.append(id);
        builder.append("_");
        builder.append(secret);
        builder.append(".jpg");
        return builder.toString();
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }
}
