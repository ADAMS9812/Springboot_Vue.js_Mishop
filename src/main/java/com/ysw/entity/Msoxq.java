package com.ysw.entity;

import java.io.Serializable;

/**
 * 订单详情实体类
 */
public class Msoxq implements Serializable {

    private Integer id;             //编号
    private String msoid;           //订单id
    private Integer count;          //购买数量
    private Integer proid;          //产品id
    private Double subtotal;        //总价
    private String pImg;            //产品图片
    private String pSn;             //商品描述

    //空参构造
    public Msoxq() {

    }

    //带参构造
    public Msoxq(Integer id, String msoid,
                 Integer count, Integer proid,
                 Double subtotal, String pImg, String pSn) {
        this.id = id;
        this.msoid = msoid;
        this.count = count;
        this.proid = proid;
        this.subtotal = subtotal;
        this.pImg = pImg;
        this.pSn = pSn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsoid() {
        return msoid;
    }

    public void setMsoid(String msoid) {
        this.msoid = msoid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getProid() {
        return proid;
    }

    public void setProid(Integer proid) {
        this.proid = proid;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getpImg() {
        return pImg;
    }

    public void setpImg(String pImg) {
        this.pImg = pImg;
    }

    public String getpSn() {
        return pSn;
    }

    public void setpSn(String pSn) {
        this.pSn = pSn;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Msoxq{");
        sb.append("id=").append(id);
        sb.append(", msoid='").append(msoid).append('\'');
        sb.append(", count=").append(count);
        sb.append(", proid=").append(proid);
        sb.append(", subtotal=").append(subtotal);
        sb.append(", pImg='").append(pImg).append('\'');
        sb.append(", pSn='").append(pSn).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
