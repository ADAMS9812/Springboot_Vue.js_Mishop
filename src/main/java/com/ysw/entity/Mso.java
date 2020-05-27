package com.ysw.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 用户 - 订单实体类
 */
public class Mso implements Serializable {

    private Integer id;         //编号
    private String msoid;       //订单id
    private String msoname;     //订单人姓名
    private String telephone;   //订单人联系方式
    private String msoTime;     //下单时间
    private String paystate;    //是否付款
    private Double subtotal;    //总金额
    private String address;     //收获地址
    private Integer userid;     //用户id
    private String deliveryState;   //是否发货
    private List<Msoxq> msoxqList;  //订单详情

    //空参构造方法
    public Mso() {

    }

    //带参构造方法
    public Mso(Integer id, String msoid, String msoname,
               String telephone, String msoTime, String paystate,
               Double subtotal, String address, Integer userid,
               String deliveryState, List<Msoxq> msoxqList) {
        this.id = id;
        this.msoid = msoid;
        this.msoname = msoname;
        this.telephone = telephone;
        this.msoTime = msoTime;
        this.paystate = paystate;
        this.subtotal = subtotal;
        this.address = address;
        this.userid = userid;
        this.deliveryState = deliveryState;
        this.msoxqList = msoxqList;
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

    public String getMsoname() {
        return msoname;
    }

    public void setMsoname(String msoname) {
        this.msoname = msoname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMsoTime() {
        return msoTime;
    }

    public void setMsoTime(String msoTime) {
        this.msoTime = msoTime;
    }

    public String getPaystate() {
        return paystate;
    }

    public void setPaystate(String paystate) {
        this.paystate = paystate;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public List<Msoxq> getMsoxqList() {
        return msoxqList;
    }

    public void setMsoxqList(List<Msoxq> msoxqList) {
        this.msoxqList = msoxqList;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Mso{");
        sb.append("id=").append(id);
        sb.append(", msoid='").append(msoid).append('\'');
        sb.append(", msoname='").append(msoname).append('\'');
        sb.append(", telephone='").append(telephone).append('\'');
        sb.append(", msoTime='").append(msoTime).append('\'');
        sb.append(", paystate='").append(paystate).append('\'');
        sb.append(", subtotal=").append(subtotal);
        sb.append(", address='").append(address).append('\'');
        sb.append(", userid=").append(userid);
        sb.append(", deliveryState='").append(deliveryState).append('\'');
        sb.append(", msoxqList=").append(msoxqList);
        sb.append('}');
        return sb.toString();
    }
}