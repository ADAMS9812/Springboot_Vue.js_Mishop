package com.ysw.service;

import com.ysw.dao.AdminDao;
import com.ysw.entity.Admin;
import com.ysw.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 管理员的service层
 */

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    /**
     * 管理员登陆方法
     *
     * @param admin
     * @return
     */
    public Admin login(Admin admin){
        return adminDao.login(admin);
    }

    /**
     * 管理员注册方法
     *
     * @param username
     * @param password
     * @return
     */
    public void adminRegister(String username,String password){

        //实例化一个admin对象,然后设置账号和密码
        Admin admin = new Admin();
        admin.setAdminName(username);
        admin.setAdminPassword(Md5Utils.setMD5(password));

        //持久化操作
        adminDao.adminRegister(admin);

        Admin admin1 = new Admin();
        admin1.setAdminName("test");
        admin1.setAdminPassword("123456");
        adminDao.adminRegister(admin1);

    }

}
