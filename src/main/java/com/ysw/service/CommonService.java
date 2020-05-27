package com.ysw.service;

import com.ysw.dao.CommentDao;
import com.ysw.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * common的service层
 */

@Service
public class CommonService {

    @Autowired
    private CommentDao commonDao;

    /**
     * 新增评论的方法
     *
     * @param comment
     */
    public void addCommon(Comment comment){
        commonDao.addCommon(comment);
    }

    /**
     * 根据产品id查询产品集合
     *
     * @return
     */
    public List<Comment> getCommentsByProid(String proid){
        return commonDao.getCommentsByProid(proid);
    }

    /**
     * 根据fid查询评论信息
     *
     * @param fid
     * @return
     */
    public Comment getCommentFid(Integer fid){
        return commonDao.getCommentFid(fid);
    }

}
