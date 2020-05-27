package com.ysw.controller;

import com.github.pagehelper.PageHelper;
import com.ysw.entity.User;
import com.ysw.service.UserService;
import com.ysw.utils.Md5Utils;
import com.ysw.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录方法
     * *哪里进来的就返回到哪个页面去
     *
     * @param user
     * @return
     */
    @GetMapping("/login")
    public User login(User user, HttpServletRequest request){

        //使用session共享登录的user对象，这里我还想使用拦截器拦截所有的操作，除非用户已经登陆
        HttpSession session = request.getSession();
        //把用户输入的密码设置为Md5码的方式进行获取
        user.setPassword(Md5Utils.setMD5(user.getPassword()));
        //根据用户名和密码寻找到User对象
        User loginUser = userService.login(user);

        //将User对象进行共享
        session.setAttribute("user",loginUser);

        //返回一个User对象
        return loginUser;
    }

    /**
     * 查询所有user账户
     *
     * @return
     */
    @GetMapping("/findAll")
    public List<User> findAll(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        //使用分页工具进行分页操作
        PageHelper.startPage(currentPage,pageSize);

        List<User> userList = userService.findAll();
        //System.out.println(userList);
        //返回带有数据的集合回去
        return userList;
    }

    /**
     * 获取总的用户数，最后返回总页数
     */
    @GetMapping("/getAllUserCount")
    public Integer getAllUserCount(){

        //获取总用户数
        Integer totalUser = userService.getAllUserCount();

        //总页数 = 总记录数/10 并向上取整
        Integer totalPage =  (int) Math.ceil(totalUser*1.0/10);

        //返回总页数
        return totalPage;
    }


    /**
     * 判断该用户名是否已经存在
     *
     * @param username
     * @return
     */
    @GetMapping("/isExist")
    public Boolean isExist(String username){
        return userService.isExist(username);
    }

    /**
     * 注册方法
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    public String register(@RequestBody User user){
        //设置注册的时间为当前的时间
        user.setRegTime(TimeUtils.getTime());
        //把密码转为MD5码的方式进行存储
        user.setPassword(Md5Utils.setMD5(user.getPassword()));
        userService.register(user);
        return "注册成功";
    }

    /**
     * 个人信息页面 - 获取登录的用户的信息内容进行展示
     *
     * @param request
     * @return
     */
    @GetMapping("/getUser")
    public User getUser(HttpServletRequest request){

        //初始化session
        HttpSession session = request.getSession();

        //获取登录的user
        User user = (User) session.getAttribute("user");

        //返回user信息
        return user;
    }

    /**
     * 更新用户user的信息内容
     *
     * @return
     */
    @GetMapping("/updateUserByUsername")
    public String updateUserByUsername(User user, HttpServletRequest request){
        //System.out.println(user);
        //初始化session
        HttpSession session = request.getSession();
        //先获取旧密码，如果旧密码跟新密码一样就不用改
        User old_user = (User) session.getAttribute("user");
        //如果旧密码跟现在的密码相同的话，就不用改密码
        if (old_user.getPassword() != null && old_user.getPassword().equals(user.getPassword())) {
            //设置密码为旧密码
            user.setPassword(user.getPassword());
        } else {
            //否则给密码设置一个MD5编码格式
            user.setPassword(Md5Utils.setMD5(user.getPassword()));
        }

        //取出session域中的user
        session.removeAttribute("user");

        //更新账号信息
        userService.updateUserByUsername(user);

        return "OK";
    }

    @DeleteMapping("/deleteByUserid/{userid}")
    public String deleteByUserid(@PathVariable Integer userid){

        //根据用户id进行账号删除操作
        try {
            userService.deleteByUserid(userid);
        } catch (Exception e) {
            return "ERROR";
        }

        return "OK";
    }

    /**
     * 根据用户id查询对象
     *
     * @param userid
     * @return
     */
    @GetMapping("/getUserById/{userid}")
    public User getUserById(@PathVariable Integer userid, HttpServletRequest request){

        //初始化session
        HttpSession session = request.getSession();

        //查询并返回一个user对象
        User user = userService.getUserById(userid);

        //如果对象是空的话就直接返回null
        if (user == null) {
            return null;
        }

        session.setAttribute("current_user",user);

        //最终返回一个user对象
        return user;
    }

    /**
     * 根据用户id进行更新操作
     *
     * @param userid
     * @param name
     * @param telephone
     * @param password
     * @param address
     * @return
     */
    @GetMapping("/updateUserById/{userid}")
    public String updateUserById(@PathVariable Integer userid,
                                 String name, String telephone,
                                 String password, String address,
                                 HttpServletRequest request){

        //初始化session
        HttpSession session = request.getSession();
        //获取存储在session中的当前的user
        User current_user = (User) session.getAttribute("current_user");

        //初始化user对象进行设置操作
        User user = new User();
        user.setId(userid);
        user.setName(name);
        user.setTelephone(telephone);
        user.setAddress(address);

        //如果当前密码与旧密码相同的话，密码就不用改了
        if (password.equals(current_user.getPassword())) {
            user.setPassword(current_user.getPassword());
        } else {
            //如果当前密码与旧密码不同，那么就直接更新新密码
            user.setPassword(Md5Utils.setMD5(password));
        }

        //根据user对象进行更新操作
        userService.updateUserById(user);

        return "OK";
    }

    /**
     * 退出当前账号的方法
     *
     * @param request
     * @return
     */
    @GetMapping("/loginOut")
    public String loginOut(HttpServletRequest request){

        //初始化session并移除user对象
        HttpSession session = request.getSession();
        session.removeAttribute("user");

        return "OK";
    }

}