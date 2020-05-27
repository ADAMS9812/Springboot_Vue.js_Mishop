package com.ysw.controller;

import com.ysw.entity.Msoxq;
import com.ysw.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayService payService;

    /**
     * 用于判断是否已经付款的方法
     *
     * @param msoid
     * @return
     */
    @GetMapping("/getPayState/{msoid}")
    public Boolean getPayState(@PathVariable String msoid){
        //判断订单是否已付款，如果是已付款就返回true
        if ("已付款".equals(payService.getPayState(msoid))) {
            return true;
        } else {
            //未付款就返回false
            return false;
        }
    }

    /**
     * 根据订单号进行支付的方法,这里主要是把订单信息返回到前端最后共享到order
     *
     * @param msoid
     * @param totalMoney
     * @return
     */
    @GetMapping("/payMoney")
    public String payMoney(String msoid, Double totalMoney, HttpServletRequest request){

        //初始化session集合
        HttpSession session = request.getSession();

        //将总价进行共享
        session.setAttribute("totalMoney",totalMoney);

        //根据订单号msoid查询msoxq并返回一个集合
        List<Msoxq> msoxqList = payService.payMoney(msoid);
        //将根据msoid得到的集合进行共享
        session.setAttribute("msoxqList",msoxqList);

        return "OK";
    }

    /**
     * 获取存储在session中的订单信息内容返回给前端
     *
     * @return
     */
    @GetMapping("/getLatePay")
    public List<Msoxq> getLatePay(HttpServletRequest request){

        //初始化session
        HttpSession session = request.getSession();
        //获取存储在session中的msoxq集合
        List<Msoxq> msoxqList = (List<Msoxq>) session.getAttribute("msoxqList");

        //返回给前端
        return msoxqList;
    }

    @GetMapping("/getTotalMoney")
    public Double getTotalMoney(HttpServletRequest request){

        //初始化session
        HttpSession session = request.getSession();
        //获取存储在session中的msoxq集合
        Double totalMoney = (Double) session.getAttribute("totalMoney");

        //返回给前端
        return totalMoney;
    }

}
