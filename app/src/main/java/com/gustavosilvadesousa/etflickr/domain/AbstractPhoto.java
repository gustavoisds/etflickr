package com.gustavosilvadesousa.etflickr.domain;

import java.io.Serializable;

public abstract class AbstractPhoto implements Serializable {
    protected String id;
    protected String secret;
    protected String farm;
    protected String server;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append("https://farm");
        builder.append(getFarm());
        builder.append(".staticflickr.com/");
        builder.append(getServer());
        builder.append("/");
        builder.append(getId());
        builder.append("_");
        builder.append(getSecret());
        builder.append(".jpg");
        return builder.toString();
    }
}
