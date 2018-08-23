package com.xdf.controller;

import com.xdf.bean.Users;
import com.xdf.service.ServiceFactory;
import com.xdf.service.user.UserService;
import com.xdf.service.user.UserServiceImpl;
import com.xdf.util.Md5Encrypt;
import com.xdf.util.ResultUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/login")
public class UserServlet extends  BaseServlet{

    //不实例化service层对象  让工厂去实例化
    private UserService  userService;
    private ResultUtil util=new ResultUtil();

    //当用户访问我们这个servlet的时候 先执行init
    @Override
    public void init() throws ServletException {
        userService=(UserService) ServiceFactory.getServiceImpl("userService");
    }

    @Override
    public Class getServletClass() {
        return UserServlet.class;
    }

    /**
     * 用户注册的方法
     */
    public  String  register(HttpServletRequest req, HttpServletResponse resp){
        //获取用户输入的参数
        String userName=req.getParameter("username");
        String password=req.getParameter("password");
        Users users=new Users();
        users.setUserName(userName);
        try {
            users.setPassword(Md5Encrypt.getEncryptedPwd(password));
            System.out.println(users.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        users.setUserType(0);  //设置用户类型
       int num= userService.add(users);
       if (num>0){
           return  "main";
       }else{
           return "register";
       }
    }


    public String login(HttpServletRequest req, HttpServletResponse resp){
        System.out.println("====>UserServlet===>login");
        //获取用户登录的用户名和密码
        String userName=req.getParameter("username");
        String password=req.getParameter("password");
        //得从数据库中获取一个用户名  如果没有用户名不需要再执行后续代码
        String passwordInDB= userService.validateName(userName); //验证用户名是否存在
        if (passwordInDB!=null){  //用户名正确  并且能找到密码
            try {
                if (Md5Encrypt.validPassword(password,passwordInDB)){ //验证密码是否正确
                    Users users=  userService.login(userName,passwordInDB);
                    //保存对象
                    req.getSession().setAttribute("loginUser",users);
                    return "main";
                }else {
                    System.out.println("密码错误");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{ //用户名错误
            System.out.println("用户名不存在");
        }
        return "login";
    }

    /**
     * ajax验证用户名是否存在
     */
    public ResultUtil  validateName(HttpServletRequest req, HttpServletResponse resp){
        //获取用户名
        String userName= req.getParameter("username");
        //调用service层代码
       String passwordInDB= userService.validateName(userName);
       if (passwordInDB==null){ //可以注册
           util.resultSuccess();
       }else {
           util.resultFail("改昵称已被占用");
       }
        return  util;
    }
}
