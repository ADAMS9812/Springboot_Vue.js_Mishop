package com.ysw.service;

import com.ysw.dao.OrderDao;
import com.ysw.entity.Mso;
import com.ysw.entity.Msoxq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 将订单插入详情表的service层
 */

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    /**
     * 将产品信息放入mso表
     *
     * @param mso
     */
    public void addToMso(Mso mso){
        orderDao.addToMso(mso);
    }

    /**
     * 将产品详情信息插入msoxq表
     *
     * @param msoxq
     */
    public void addToMsoXq(Msoxq msoxq){
        orderDao.addToMsoXq(msoxq);
    }

    /**
     * 支付功能方法，更新订单的支付状态
     *
     * @param mso
     */
    public void payMoney(Mso mso){
        orderDao.payMoney(mso);
    }

    /**
     * 根据用户id查询订单产品集合
     *
     * @param userid
     * @return
     */
    public List<Mso> getMsoByUserid(Integer userid){
        return orderDao.getMsoByUserid(userid);
    }

    /**
     * 根据msoid来查询我们的msoxq的集合
     *
     * @param msoid
     * @return
     */
    public List<Msoxq> getMsoxqByMsoid(String msoid){
        return orderDao.getMsoxqByMsoid(msoid);
    }

    /**
     * 根据用户id查询mso的总数量
     *
     * @param userid
     * @return
     */
    public Integer getMsoCountByUserid(Integer userid){
        return orderDao.getMsoCountByUserid(userid);
    }

    /**
     * 根据msoid查询总的产品订单数
     *
     * @param msoid
     * @return
     */
    public Integer getMsoxqCountByMsoid(String msoid){
        return orderDao.getMsoxqCountByMsoid(msoid);
    }

    /**
     * 查询所有的订单
     *
     * @return
     */
    public List<Mso> findAll(){
        return orderDao.findAll();
    }

    /**
     * 返回订单总数
     *
     * @return
     */
    public Integer getTotalCount(){
        return orderDao.getTotalCount();
    }

    /**
     * 根据msoid删除订单
     *
     * @param msoid
     */
    public void deleteByMsoid(String msoid){
        orderDao.deleteMsoByMsoid(msoid);
        orderDao.deleteMsoxqByMsoid(msoid);
    }

    /**
     * 根据msoid更新货物的发货状态
     *
     * @param msoid
     */
    public void sendPro(String msoid){
        orderDao.sendPro(msoid);
    }

}
