package com.ysw.entity;

import java.io.Serializable;

/**
 * 管理员登录实体类
 */
public class Admin implements Serializable {

    private Integer adminId;            //管理员id
    private String adminName;           //管理员姓名
    private String adminPassword;       //管理员密码

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Admin{");
        sb.append("adminId=").append(adminId);
        sb.append(", adminName='").append(adminName).append('\'');
        sb.append(", adminPassword='").append(adminPassword).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
