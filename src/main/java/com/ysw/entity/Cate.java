package com.ysw.entity;

import java.io.Serializable;

/**
 * 类别管理
 */

public class Cate implements Serializable {

    private Integer id;         //产品id
    private String cName;       //产品名

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Cate{");
        sb.append("id=").append(id);
        sb.append(", cName='").append(cName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
