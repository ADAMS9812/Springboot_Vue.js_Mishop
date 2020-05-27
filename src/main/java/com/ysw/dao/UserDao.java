package com.ysw.dao;

import com.ysw.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    /**
     * 登录方法
     *
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 查询所有
     *
     * @return
     */
    List<User> findAll();

    /**
     * 获取总的用户数
     *
     * @return
     */
    Integer getAllUserCount();

    /**
     * 注册方法
     *
     * @param user
     */
    void register(User user);

    /**
     * 判断用户名是否已经存在
     *
     * @param username
     * @return
     */
    User isExist(String username);

    /**
     * 更新用户信息
     *
     * @param user
     */
    void updateUserByUsername(User user);

    /**
     * 根据用户id进行删除操作
     *
     * @param id
     */
    void deleteByUserid(Integer id);

    /**
     * 根据用户id查询对象并返回
     *
     * @param userid
     * @return
     */
    User getUserById(Integer userid);

    /**
     * 根据用户id进行更新操作
     *
     * @param user
     */
    void updateUserById(User user);

}
