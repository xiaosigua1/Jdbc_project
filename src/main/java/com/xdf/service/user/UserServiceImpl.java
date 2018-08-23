package com.xdf.service.user;

import com.xdf.bean.Users;
import com.xdf.dao.user.UserDao;
import com.xdf.dao.user.UserDaoImpl;
import com.xdf.util.PageUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 在不改变dao层代码的前提下，做一些业务逻辑！
 * 增强处理==》增加系统级业务
 */
public class UserServiceImpl implements UserService {

    //耦合 加上 静态代理
    private UserDao userDao=new UserDaoImpl();

    @Override
    public int add(Users users) {
        return  userDao.add(users);
    }

    @Override
    public int deleteByCondition(Serializable id) {
        return userDao.deleteByCondition(id);
    }

    @Override
    public int update(Users users) {
        return 0;
    }

    @Override
    public Users findByCondition(Serializable id) {
        return null;
    }

    @Override
    public List<Users> findAll() {
        return null;
    }

    @Override
    public int findRownum() {
        return userDao.findRownum();
    }

    @Override
    public List<Users> findAllByPage(PageUtil util, Object... params) {
        return userDao.findAllByPage(util);
    }

    /**
     * 验证用户名是否存在
     * @param userName
     * @return
     */
    @Override
    public String validateName(String userName) {
        return userDao.validateName(userName);
    }

    /**
     * 登录操作
     */
    @Override
    public Users login(String userName, String password) {
        return userDao.login(userName,password);
    }
}
