package com.xdf.controller;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * BaseServlet是所有子servlet的父类
 * 这个类写什么？
 *   所有的请求都会进入此类！
 *   获取请求，根据请求分发到各个子servlet！
 *   我们怎么知道用户访问哪个子servlet？
 *   就算我们找到了servlet还得确定需要执行的方法？
 *   方法返回的数据有几种类型？
 *      01.json类型的数据
 *      02.普通字符串 ==》页面名称
 *    返回方式？
 *      01.转发
 *      02.重定向
 *      03.不返回==》 ajax
 *    extends HttpServlet
 */
public abstract class BaseServlet extends HttpServlet{

    /**
     *  因为所有的子 servlet都 需要继承这个BaseServlet
     */
      public   abstract  Class  getServletClass();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      //获取用户的请求  请求中必须携带一个参数===》方法名称 methodName
     String methodName=  req.getParameter("methodName");
     //根据用户传递的参数   确定执行哪个子servlet中的这个methodName方法
      //用户需要指定的方法
        Method method=null;
      //执行方法的返回值
        Object result=null;
        if (methodName==null||"".equals(methodName)){
            result= execute(req,resp);  //统一返回到 主页面
        }else {  //证明有方法  必须先确定哪个servlet
            try {
                //找到方法
                method= getServletClass().getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
                result= method.invoke(this,req,resp); //执行方法
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //根据不同的返回值 执行不同的操作
        toView(req,resp,result);
    }

    /**
     * @param req  请求
     * @param resp  响应
     * @param result 方法的返回值
     */
    private void toView(HttpServletRequest req, HttpServletResponse resp, Object result) throws ServletException, IOException {
       if (result==null){
       }else {  //要么 json  要么字符串
            if (result instanceof String){//返回值字符串
                String  viewName=result.toString()+".jsp";
                req.getRequestDispatcher(viewName).forward(req,resp);
            }else{//返回值是json
               String resultJson= (String) JSON.toJSONString(result);
                PrintWriter writer=resp.getWriter();
                writer.write(resultJson);
                writer.flush();
                writer.close();
            }
       }

    }

    private Object execute(HttpServletRequest req, HttpServletResponse resp) {
        return "main";  //返回主页面
    }
}
