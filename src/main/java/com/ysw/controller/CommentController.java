package com.ysw.controller;

import com.ysw.entity.Comment;
import com.ysw.entity.User;
import com.ysw.service.CommonService;
import com.ysw.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于发表评论的controller层
 * 这里记得不要用responsebody，否则返回的是json字符串
 */

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommonService commonService;

    /**
     * 设置产品id设置进session域
     *
     * @param proid
     * @param request
     * @return
     */
    @RequestMapping("/setProid/{proid}")
    @ResponseBody
    public String setProid(@PathVariable String proid, HttpServletRequest request){

        //初始化session
        HttpSession session = request.getSession();
        //设置共享proid
        session.setAttribute("current_proid",proid);

        return "OK";
    }

    /**
     * 获取session域中的proid
     *
     * @param request
     * @return
     */
    @RequestMapping("/getProid")
    @ResponseBody
    public String getProid(HttpServletRequest request){

        //初始化session
        HttpSession session = request.getSession();
        //设置共享proid
        String proid = (String) session.getAttribute("current_proid");

        return proid;
    }

    /**
     * 用于新增评论的方法
     *
     * @param comment
     * @return
     */
    @RequestMapping("/addComment")
    public String addCommon(Comment comment, HttpServletRequest request){

        //初始化session
        HttpSession session = request.getSession();
        //获取user
        User user = (User) session.getAttribute("user");
        //看看user对象是否为空，如果不为空的话就设置user里面的属性内容存到数据库
        if (user == null) {
            //如果user对象为空就直接返回ERROR
            return "redirect:/error.html";
        } else {
            //设置用户的id号
            comment.setUserid(user.getId());
            //设置评论时间
            comment.setTime(TimeUtils.getTime());
            //保存到数据库
            commonService.addCommon(comment);
            //输出测试
            //System.out.println(comment);
            return "redirect:/success.html";
        }
    }

    /**
     * 根据id返回产品集合
     * 不是restController不能用getmapping
     * @param request
     * @return
     */
    @RequestMapping("/getCommentsByProid")
    @ResponseBody
    public List<Comment> getComment(HttpServletRequest request){

        //初始化session
        HttpSession session = request.getSession();
        //设置共享proid
        String proid = (String) session.getAttribute("current_proid");
        //根据id返回产品集合
        return commonService.getCommentsByProid(proid);

    }

    /**
     * 按fid查询评论信息（评论展示）
     *
     * @return
     */
    @RequestMapping("/getCommentFid/{fid}")
    @ResponseBody
    public Comment getCommentFid(@PathVariable Integer fid) {

        //根据fid查询帖子
        Comment comment = commonService.getCommentFid(fid);

        //返回一个comment对象
        return comment;
    }

}
