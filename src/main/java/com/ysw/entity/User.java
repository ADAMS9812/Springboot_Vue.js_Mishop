package com.ysw.entity;

import java.io.Serializable;

/**
 * 用户实体类
 */
public class User implements Serializable {

    private Integer id;         //用户id
    private String username;    //账号
    private String password;    //密码
    private String telephone;   //手机号
    private String name;        //用户名
    private String regTime;     //注册时间
    private String gexing;      //个性签名
    private String hobby;       //爱好
    private String address;     //地址

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getGexing() {
        return gexing;
    }

    public void setGexing(String gexing) {
        this.gexing = gexing;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", telephone='").append(telephone).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", regTime='").append(regTime).append('\'');
        sb.append(", gexing='").append(gexing).append('\'');
        sb.append(", hobby='").append(hobby).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
