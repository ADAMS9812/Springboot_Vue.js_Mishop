package com.ysw.dao;

import com.ysw.entity.Mso;
import com.ysw.entity.Msoxq;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单接口
 */

@Repository
public interface OrderDao {

    /**
     * 查询所有的订单
     *
     * @return
     */
    List<Mso> findAll();

    /**
     * 将单个订单插入mso订单表
     *
     * @param mso
     */
    void addToMso(Mso mso);

    /**
     * 将订单详情内容
     *
     * @param msoxq
     */
    void addToMsoXq(Msoxq msoxq);

    /**
     * 根据mso对象更新订单的支付状态
     *
     * @param mso
     * @return
     */
    void payMoney(Mso mso);

    /**
     * 根据用户id查询mso
     *
     * @param userid
     * @return
     */
    List<Mso> getMsoByUserid(Integer userid);

    /**
     * 根据msoid来查询msoxq的内容
     *
     * @param msoid
     * @return
     */
    List<Msoxq> getMsoxqByMsoid(String msoid);

    /**
     * 根据用户id查询mso总数
     *
     * @param userid
     * @return
     */
    Integer getMsoCountByUserid(Integer userid);

    /**
     * 根据订单msoid查询msoxq的总数量
     *
     * @param msoid
     * @return
     */
    Integer getMsoxqCountByMsoid(String msoid);

    /**
     * 返回订单总数
     *
     * @return
     */
    Integer getTotalCount();

    /**
     * 根据msoid删除msoxq中的订单
     */
    void deleteMsoxqByMsoid(String msoid);

    /**
     * 根据msoid删除mso中的订单
     */
    void deleteMsoByMsoid(String msoid);

    /**
     * 根据msoid更新货物的发货状态
     *
     * @param msoid
     */
    void sendPro(String msoid);
}