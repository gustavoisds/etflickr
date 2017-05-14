package com.gustavosilvadesousa.etflickr.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class User implements Serializable {

    private String nsid;
    @JsonProperty("fullname")
    private String fullName;
    @JsonProperty("username")
    private String userName;

    public String getNsid() {
        return nsid;
    }

    public void setNsid(String nsid) {
        this.nsid = nsid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
