package com.ysw.entity;

import java.io.Serializable;

/**
 * 单个产品展示用的实体类
 */

public class ProDetail implements Serializable {

    private Integer id;
    private String pName;
    private Integer pNum;
    private Double iPrice;
    private String pImg;
    private String xqImg;
    private String cName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Integer getpNum() {
        return pNum;
    }

    public void setpNum(Integer pNum) {
        this.pNum = pNum;
    }

    public Double getiPrice() {
        return iPrice;
    }

    public void setiPrice(Double iPrice) {
        this.iPrice = iPrice;
    }

    public String getpImg() {
        return pImg;
    }

    public void setpImg(String pImg) {
        this.pImg = pImg;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getXqImg() {
        return xqImg;
    }

    public void setXqImg(String xqImg) {
        this.xqImg = xqImg;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProDetail{");
        sb.append("id=").append(id);
        sb.append(", pName='").append(pName).append('\'');
        sb.append(", pNum=").append(pNum);
        sb.append(", iPrice=").append(iPrice);
        sb.append(", pImg='").append(pImg).append('\'');
        sb.append(", xqImg='").append(xqImg).append('\'');
        sb.append(", cName='").append(cName).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
