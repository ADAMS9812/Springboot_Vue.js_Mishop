package com.ysw.entity;

import java.io.Serializable;

/**
 * 用户评论的实体类
 */

public class Comment implements Serializable {

    private Integer fid;        //评论id号
    private String titles;      //标题
    private Integer proid;      //产品号
    private Integer userid;     //用户id
    private String username;    //用户名
    private String fcontent;    //评论内容
    private String time;        //评论时间

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public Integer getProid() {
        return proid;
    }

    public void setProid(Integer proid) {
        this.proid = proid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFcontent() {
        return fcontent;
    }

    public void setFcontent(String fcontent) {
        this.fcontent = fcontent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Comment{");
        sb.append("fid=").append(fid);
        sb.append(", titles='").append(titles).append('\'');
        sb.append(", proid=").append(proid);
        sb.append(", userid=").append(userid);
        sb.append(", username='").append(username).append('\'');
        sb.append(", fcontent='").append(fcontent).append('\'');
        sb.append(", time='").append(time).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
