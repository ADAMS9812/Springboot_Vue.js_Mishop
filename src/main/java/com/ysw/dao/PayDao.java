package com.ysw.dao;

import com.ysw.entity.Msoxq;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayDao {

    /**
     * 根据订单号查询mso中该订单是否已经支付
     *
     * @param msoid
     * @return
     */
    String getPayState(String msoid);

    /**
     * 根据订单号查询msoxq中的订单进行支付操作
     *
     * @param msoid
     * @return
     */
    List<Msoxq> payMoney(String msoid);

}
