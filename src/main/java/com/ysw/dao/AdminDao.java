package com.ysw.dao;

import com.ysw.entity.Admin;
import org.springframework.stereotype.Repository;

/**
 * 管理员的dao层操作
 */

@Repository
public interface AdminDao {

    /**
     * 登录方法
     *
     * @param admin
     * @return
     */
    Admin login(Admin admin);

    /**
     * 注册方法
     *
     * @param admin
     */
    void adminRegister(Admin admin);

}
