package com.ysw.controller;

import com.github.pagehelper.PageHelper;
import com.ysw.entity.Mso;
import com.ysw.entity.Msoxq;
import com.ysw.entity.Pro;
import com.ysw.entity.User;
import com.ysw.service.OrderService;
import com.ysw.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 订单模块的Controller层
 */

@RestController     //@ResponseBody + @Controllerd结合
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 将购物车的产品添加入订单中心
     * 这里还是从session中获取cart的内容然后赋值过去
     * 最后保存到数据库的
     *
     * @param order_ids     产品id的数组
     * @param totalMoney    总价格
     * @return
     */
    @GetMapping("/addToOrder")
    public String addToOrder(String order_ids, String totalMoney, HttpServletRequest request){

        //初始化session
        HttpSession session = request.getSession();
        //获取购物车的内容,记住添加到订单中心后我们的cart就为空了
        List<Pro> cart = (List<Pro>)session.getAttribute("cart");

        //获取登录之后的user对象
        User user = (User) session.getAttribute("user");
        //如果user用户为空的话就直接要求用户进行登录
        if (user == null) {
            return "ERROR_NOLOGIN";
        }

        //订单号（根据时间生成，所以是共享的）
        String orderNumber = TimeUtils.getOrderNumber();

        //用于存放订单详情的集合
        List<Msoxq> msoxqs = new ArrayList<>();

        //迭代器获取购物车里面的所有内容
        Iterator it = cart.iterator();
        while (it.hasNext()) {

            //把迭代出来的每一个对象都赋值给对象pro
            Pro pro = (Pro)it.next();

            //获取订单详情的内容，然后放入list集合
            Msoxq msoxq = new Msoxq(null,orderNumber,pro.getCurrentCount(),pro.getId(),
                    pro.getCurrentCount()*pro.getiPrice(),pro.getpImg(),pro.getpSn());

            //把这个对象信息放入集合
            msoxqs.add(msoxq);

            //把这个对象信息插入msoxq表
            orderService.addToMsoXq(msoxq);

            //System.out.println(msoxq);
        }

        //这里设置pojo对象的属性值,并且保存到数据库
        Mso mso = new Mso(null,orderNumber,user.getName(),user.getTelephone(),TimeUtils.getTime(),
                "未付款",Double.parseDouble(totalMoney),user.getAddress(),user.getId(),"未发货",msoxqs);

        //将这个信息放入mso表
        orderService.addToMso(mso);

        //把mso信息设置进session域进行共享
        session.setAttribute("order_mso",mso);

        //System.out.println(mso);

        //System.out.println("id集合：" + order_ids);
        //System.out.println("总价：" + totalMoney);

        return "OK";
    }

    /**
     * 获取订单详情里面的所有内容
     *
     * @param request
     * @return
     */
    @GetMapping("/getOrder")
    public Mso getMsos(HttpServletRequest request){
        //获取session里面的订单信息内容
        HttpSession session = request.getSession();
        //获取订单session里面的集合
        Mso mso = (Mso) session.getAttribute("order_mso");
        //如果mso对象不为空的话，就可以直接返回，否则直接返回null
        if (mso == null) {
            return null;
        }
        //直接返回给订单页面
        return mso;
    }

    /**
     * 获取
     *
     * @param request
     * @return
     */
    @GetMapping("/getLoginUser")
    public User getLoginUser(HttpServletRequest request){
        //初始化session
        HttpSession session = request.getSession();
        //获取session里面的user对象
        User user = (User) session.getAttribute("user");
        //如果用户暂未登录的话就返回null
        if (user == null) {
            return null;
        }
        //返回一个user对象
        return user;
    }

    /**
     * 支付用的方法
     * 主要是根据产品id和用户id进行订单的更新
     *
     * @param totalMoney    总金额
     * @param msoid         订单号
     * @param userid        用户id
     * @return
     */
    @GetMapping("/payMoney")
    public Mso payMoney(Double totalMoney, String msoid, Integer userid, HttpServletRequest request){

        //初始化session
        HttpSession session = request.getSession();

        //将三个参数set进一个Mso对象，用于更新订单的支付状态
        Mso mso = new Mso();
        mso.setSubtotal(totalMoney);
        mso.setMsoid(msoid);
        mso.setUserid(userid);
        mso.setPaystate("已付款");

        //更改订单支付状态
        orderService.payMoney(mso);

        //支付成功后清空session域中的该产品
        session.removeAttribute("order_mso");

        System.out.println(mso);

        return mso;
    }

    /**
     * 根据用户id查询mso产品集合
     */
    @GetMapping("/getMsoByUserid")
    public List<Mso> getMsoByUserid(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                                    @RequestParam(value = "pageSize",defaultValue = "3") Integer pageSize,
                                    HttpServletRequest request){

        //分页工具
        PageHelper.startPage(currentPage,pageSize);

        //初始化session
        HttpSession session = request.getSession();
        //获取session域中的user对象
        User user = (User) session.getAttribute("user");

        //获取用户的id号
        Integer userid = user.getId();

        //根据用户id查询mso集合
        List<Mso> msoList = orderService.getMsoByUserid(userid);

        //返回一个带有数据的给前端
        return msoList;
    }

    /**
     * 根据订单号查询集合并存入session,每次执行都会覆盖一次
     *
     * @param msoid
     * @return
     */
    @GetMapping("/getMsoxqByMsoid/{msoid}")
    public String getMsoxqByMsoid(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                                  @RequestParam(value = "pageSize",defaultValue = "3") Integer pageSize,
                                  @PathVariable String msoid, HttpServletRequest request){

        //分页工具
        PageHelper.startPage(currentPage,pageSize);

        //初始化session对象
        HttpSession session = request.getSession();

        //根据msoid来查询我们的msoxq的集合的内容
        List<Msoxq> msoxqList = orderService.getMsoxqByMsoid(msoid);

        //共享当前的msoid
        session.setAttribute("current_msoid",msoid);

        //设置进session域进行共享操作
        session.setAttribute("msoxq",msoxqList);

        return "OK";
    }

    /**
     * 获取存在session域中的产品
     *
     * @param request
     * @return
     */
    @GetMapping("/pageMsoxq")
    public List<Msoxq> pageMsoxq(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                                 @RequestParam(value = "pageSize",defaultValue = "3") Integer pageSize,
                                 HttpServletRequest request){

        //分页工具
        PageHelper.startPage(currentPage,pageSize);

        //初始化session
        HttpSession session = request.getSession();

        //获取存储在session域中的msoid
        String msoid = (String) session.getAttribute("current_msoid");

        //获取session域中的内容
        List<Msoxq> msoxqList = orderService.getMsoxqByMsoid(msoid);

        return msoxqList;
    }

    /**
     * 获取存在session域中的产品
     *
     * @param request
     * @return
     */
    @GetMapping("/getMsoxq")
    public List<Msoxq> getMsoxq(HttpServletRequest request){

        //初始化session
        HttpSession session = request.getSession();
        //获取session域中的内容
        List<Msoxq> msoxqList = (List<Msoxq>) session.getAttribute("msoxq");

        return msoxqList;
    }

    @GetMapping("/getMsoCountByUserid")
    public Integer getMsoCountByUserid(HttpServletRequest request){

        //初始化session
        HttpSession session = request.getSession();
        //获取session域中的user
        User user = (User) session.getAttribute("user");
        //根据用户id查询mso总记录数
        Integer current_totalPage = orderService.getMsoCountByUserid(user.getId());
        //返回总页数
        int totalPage = (int) (Math.ceil(current_totalPage*1.0/3));

        //mso的总页数
        return totalPage;
    }

    @GetMapping("/getMsoxqCountByMsoid")
    public Integer getMsoxqCountByMsoid(HttpServletRequest request){

        //初始化session
        HttpSession session = request.getSession();
        //获取session域中的msoxqList
        List<Msoxq> msoxqList = (List<Msoxq>) session.getAttribute("msoxq");

        //System.out.println(msoxqList);

        //初始化msoid
        String msoid = "";
        //迭代器
        Iterator it = msoxqList.iterator();
        //通过迭代器获取集合里面的内容
        if (it.hasNext()){
            //并且给我们的msoid进行赋值
            Msoxq msoxq = (Msoxq) it.next();
            msoid = msoxq.getMsoid();
        }

        //根据msoid获取总记录数
        Integer current_totalPage = orderService.getMsoxqCountByMsoid(msoid);

        //返回总页数
        int totalPage = (int) (Math.ceil(current_totalPage*1.0/3));

        //mso的总页数
        return totalPage;
    }

    /**
     * 查询所有订单的方法
     *
     * @return
     */
    @GetMapping("/findAll")
    public List<Mso> findAll(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                             @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){

        //分页操作
        PageHelper.startPage(currentPage,pageSize);

        //返回一个分页好的集合
        return orderService.findAll();
    }

    /**
     * 返回订单总数
     *
     * @return
     */
    @GetMapping("/getTotalPage")
    public Integer getTotalPage(){

        //先获取总记录数
        Integer totalCount = orderService.getTotalCount();

        //计算总页数：总记录数 / 10 最后向上取整
        Integer totalPage = (int) Math.ceil(totalCount*1.0/10);

        //返回总页数
        return totalPage;
    }

    /**
     * 根据msoid删除订单
     *
     * @param msoid
     * @return
     */
    @GetMapping("/deleteByMsoid/{msoid}")
    public String deleteByMsoid(@PathVariable String msoid) {

        //根据订单号同时删除mso和msoxq表中的订单
        try {
            orderService.deleteByMsoid(msoid);
        } catch (Exception e) {
            return "ERROR";
        }

        return "OK";
    }

    /**
     * 根据订单msoid获取msoxq集合
     *
     * @param msoid
     * @return
     */
    @GetMapping("/getMsoxqByMsoid_admin/{msoid}")
    public List<Msoxq> getMsoxqByMsoid_admin(@PathVariable String msoid){
        //根据msoid来查询我们的msoxq的集合的内容
        List<Msoxq> msoxqList = orderService.getMsoxqByMsoid(msoid);
        //返回集合
        return msoxqList;
    }

    @GetMapping("/sendPro/{msoid}")
    public String sendPro(@PathVariable String msoid){

        try {
            //更具订单id更新货物的发货状态
            orderService.sendPro(msoid);
        } catch (Exception e) {
            return "ERROR";
        }

        return "OK";
    }

}
