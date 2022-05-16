package com.zoey.req;

public class UserQueryReq extends PageReq{

    private String loginName;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String name) {
        this.loginName = name;
    }

    @Override
    public String toString() {
        return "UserQueryReq{" +
                "loginName=" + loginName +
                "} " + super.toString();
    }
}