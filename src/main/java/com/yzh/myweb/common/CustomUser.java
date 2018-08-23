package com.yzh.myweb.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomUser implements Serializable {

    private int  id;

    private String  userName;

    private String userType;//10:买方 ; 20:卖方 ; 30:保理商

    private String pwd;

    private String isManager;

    public CustomUser(){}

    @Override
    public String toString() {
        return "CustomUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userType='" + userType + '\'' +
                ", pwd='" + pwd + '\'' +
                ", isManager='" + isManager + '\'' +
                '}';
    }
}
