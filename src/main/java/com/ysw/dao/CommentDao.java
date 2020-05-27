package com.ysw.dao;

import com.ysw.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao {

    /**
     * 新增评论的方法
     *
     * @param comment
     */
    void addCommon(Comment comment);

    /**
     * 根据输入的proid查询集合
     *
     * @param proid
     * @return
     */
    List<Comment> getCommentsByProid(String proid);

    /**
     * 根据fid查询评论信息
     *
     * @param fid
     * @return
     */
    Comment getCommentFid(Integer fid);

}
