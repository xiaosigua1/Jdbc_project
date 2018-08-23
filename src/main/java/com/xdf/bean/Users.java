package com.xdf.bean;

import java.io.Serializable;

public class Users implements Serializable {

    private Integer users_id; //用户ID
    private String userName;//用户名
    private String password;//用户名
    private String email;//email
    private String File;
    private Integer userType;//用户类型 0：管理员 1：普通用户

    public String getFile() {
        return File;
    }

    public void setFile(String file) {
        File = file;
    }

    public Users() {
    }

    public Users(Integer user_id, String userName, String password, String email, Integer userType) {
        this.users_id = user_id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.userType = userType;
    }

    public Integer getUsers_id() {
        return users_id;
    }

    public void setUsers_id(Integer user_id) {
        this.users_id = user_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "Users{" +
                "user_id=" + users_id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                '}';
    }
}
