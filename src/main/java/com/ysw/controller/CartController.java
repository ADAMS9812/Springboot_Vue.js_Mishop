package com.ysw.controller;

import com.ysw.entity.Mso;
import com.ysw.entity.Pro;
import com.ysw.service.ProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 购物车模块的controller层
 */

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProService proService;

    /**
     * 把产品根据pro.id添加到购物车
     *
     * @return
     */
    @PostMapping("/addCart/{id}")
    public String addCart(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response){

        //根据id查询产品
        Pro pro = proService.getProById(id);

        //如果该产品没有库存了的话就直接返回没有库存了
        if (pro.getpNum() == 0) {
            return "该产品没有库存了！";
        }

        //获取session的实例
        HttpSession session = request.getSession();

        /*
            *购物车实现的思路：
                首先我们实例化session，查询在之前是否已经存有了一个cart的list集合
                    如果存在list集合的话就直接remove掉它，然后加入一个新的pro对象进去（主要是购买的产品数不同）
                    如果不存在list集合的话就把该list对象new出来，然后再把产品放进去

                如果存在cart，并且我们要在这个cart里面存入相同的产品的话，就把该产品的数量 + 1即可
         */

        //这里我们使用HashMap的方式来存储我们的产品，键存产品对象，值存产品数量
        List<Pro> cart = (List<Pro>) session.getAttribute("cart");

        //首先根据proid获取数量，看下在session域中是否存在改产品的数量存储信息
        String proCount = (String) session.getAttribute(pro.getId() + "_proCount");

        //用于记录购买当前产品的数量
        Integer current_proNum = 0;

        //如果不存在该产品的数量存储信息的话，就直接初始化数量为1
        if (proCount == null) {
            current_proNum = 1;
            //然后把此时的数量存入session
            session.setAttribute(pro.getId() + "_proCount","" + current_proNum);
        }

        //如果cart购物车里面的内容为空的话，就直接new一个出来即可
        if (cart == null) {
            //创建一个cart对象
            cart = new ArrayList<Pro>();
        }

        //判断cart集合中是否存在该pro对象（重写了hashcode()和equals()方法，根据id进行判断的）
        if (cart.contains(pro)) {
            //获取cart中指定pro（根据id来获取）的value值（产品个数） + 1
            current_proNum = Integer.parseInt((String)session.getAttribute(pro.getId() + "_proCount")) + 1;
            //重新保存到session域中去
            session.setAttribute(pro.getId() + "_proCount","" + current_proNum);
            //如果在购物车中就已经包含了这个pro的话，就直接删掉，重新add一个新的进来即可
            cart.remove(pro);
        }

        //把当前数量存储到pro对象中去
        pro.setCurrentCount(current_proNum);

        //把产品放入cart集合
        cart.add(pro);

        //存入session
        session.setAttribute("cart",cart);

        return "添加成功！";
    }

    /**
     * 获取购物车里面的所有内容进行展示
     *
     * @param request
     * @return
     */
    @GetMapping("/getCart")
    public List<Pro> getCart(HttpServletRequest request){

        //获取session的实例
        HttpSession session = request.getSession();

        //这里我们使用HashMap的方式来存储我们的产品，键存产品对象，值存产品数量
        List<Pro> cart = (List<Pro>) session.getAttribute("cart");

        //这个是放在购物车内的所有产品的数据，是一个map集合
        return cart;
    }

    @GetMapping("/getCartTotalMoney")
    public Double getCartTotalMoney(HttpServletRequest request){

        //获取session的实例
        HttpSession session = request.getSession();

        //这里我们使用HashMap的方式来存储我们的产品，键存产品对象，值存产品数量
        List<Pro> cart = (List<Pro>) session.getAttribute("cart");

        Double totalMoney = 0.0;

        //如果购物车不为空的话才可以赋予价格
        if (cart != null) {
            //迭代器获取cart
            Iterator it = cart.iterator();
            while (it.hasNext()) {
                Pro pro = (Pro) it.next();
                //累加获取总价
                totalMoney += pro.getCurrentCount()*pro.getiPrice();
            }
        }

        return totalMoney;
    }

    @DeleteMapping("/deleteProById/{proid}")
    public String deleteProById(@PathVariable Integer proid, HttpServletRequest request){

        //根据id查询产品
        Pro pro = proService.getProById(proid);

        //获取session的实例
        HttpSession session = request.getSession();

        //获取cart集合里面的所有内容
        List<Pro> cart = (List<Pro>) session.getAttribute("cart");

        //判断在cart里面是否包含这个集合
        if (cart.contains(pro)) {
            //首先根据proid获取数量，看下在session域中是否存在改产品的数量存储信息
            String current_number = (String) session.getAttribute(pro.getId() + "_proCount");
            //如果改变量不为空则说明存在该产品的数量
            if (current_number != null) {
                session.removeAttribute(proid + "_proCount");
            } else {
                return "删除异常！";
            }
            //删除cart集合里面的pro产品
            cart.remove(pro);

        } else {
            //如果不包含这个产品的话就无法删除，返回此商品不存在即可
            return "此商品不存在！";
        }

        return "删除成功！";
    }

    /**
     * 清空购物车
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/clearCart")
    public String clearCart(String ids, HttpServletRequest request){

        //获取session的实例
        HttpSession session = request.getSession();

        //获取cart集合里面的所有内容
        List<Pro> cart = (List<Pro>) session.getAttribute("cart");

        //输出ids数组查看
        //System.out.println(ids);

        //根据,进行切割，取出每一个id
        String[] id = ids.split(",");

        //遍历取出所有的id
        for (int i = 0; i < id.length; i++) {
            //根据id查询产品
            Pro pro = proService.getProById(Integer.parseInt(id[i]));
            //如果在cart中存在的话就直接清空即可
            if (cart.contains(pro)) {
                //清空session中的所有产品
                cart.remove(pro);

                //清空session中关于该产品的数量信息
                session.removeAttribute(Integer.parseInt(id[i]) + "_proCount");
            }
        }

        return "购物车清空成功！";
    }

    /**
     * 更新购物车中的产品数量
     *
     * @param proid
     * @param count
     */
    @GetMapping("/updateCartPro")
    public void updateCartPro(@RequestParam(value = "proid_update") Integer proid,
                              @RequestParam(value = "count_update") Integer count, HttpServletRequest request){

        //根据id查询产品
        Pro pro = proService.getProById(proid);

        //获取session的实例
        HttpSession session = request.getSession();

        //获取cart集合里面的所有内容
        List<Pro> cart = (List<Pro>) session.getAttribute("cart");

        //首先判断cart购物车中是否包含此产品
        if (cart.contains(pro)) {
            //产品数量的更改（主要是通过覆盖来进行的）
            session.setAttribute(pro.getId() + "_proCount","" + count);
            //清空掉原来存储在cart中的pro
            cart.remove(pro);
        }

        //设置产品购买的数量返回给前端
        pro.setCurrentCount(Integer.parseInt((String)session.getAttribute(pro.getId() + "_proCount")));

        //System.out.println(pro);

        //把产品放入cart集合
        cart.add(pro);

        //存入session
        session.setAttribute("cart",cart);

    }

}