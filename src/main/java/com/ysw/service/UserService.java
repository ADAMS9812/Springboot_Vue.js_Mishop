package com.ysw.service;

import com.ysw.dao.UserDao;
import com.ysw.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 登录方法
     *
     * @param user
     * @return
     */
    public User login(User user){
        return userDao.login(user);
    }

    /**
     * 查询所有的方法
     *
     * @return
     */
    public List<User> findAll(){
        return userDao.findAll();
    }

    /**
     * 获取总的用户数
     *
     * @return
     */
    public Integer getAllUserCount(){
        return userDao.getAllUserCount();
    }

    /**
     * 注册方法
     *
     * @param user
     */
    public void register(User user){
        userDao.register(user);
    }

    /**
     * 查询用户名是否已经存在
     *
     * 存在    返回User
     * 不存在  返回null
     *
     * @param username
     * @return
     */
    public Boolean isExist(String username){

        //如果已经存在就返回true，如果不存在就返回false
        Boolean isExistOrNot = false;

        //判断是否能根据username查询到一个对象，如果对象不为空则说明该用户名的对象已经存在
        if (userDao.isExist(username) != null) {
            //用户已存在
            isExistOrNot = true;
        } else {
            //用户不存在
            isExistOrNot = false;
        }
        return isExistOrNot;
    }

    /**
     * 根据username更新用户信息
     *
     * @param user
     */
    public void updateUserByUsername(User user){
        userDao.updateUserByUsername(user);
    }

    /**
     * 根据用户id进行删除
     *
     * @param id
     */
    public void deleteByUserid(Integer id){
        userDao.deleteByUserid(id);
    }

    /**
     * 根据用户id查询对象并返回
     *
     * @param userid
     * @return
     */
    public User getUserById(Integer userid){
        return userDao.getUserById(userid);
    }

    /**
     * 根据用户id进行更新
     *
     * @param user
     */
    public void updateUserById(User user){
        userDao.updateUserById(user);
    }

}