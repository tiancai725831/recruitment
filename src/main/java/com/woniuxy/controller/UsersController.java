package com.woniuxy.controller;


import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.util.CodeMessage;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-06
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    //发送验证码方法
    @GetMapping("sendVerificationCode")
    public Result sendVerificationCode(String phone, HttpServletRequest request, HttpServletResponse response){
        System.out.println("进入发送验证码方法");
        System.out.println("获得的电话"+phone);
        String tel=phone;
        //表达式判断电话号码是否正确
        String judge="^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$";
        Pattern r = Pattern.compile(judge);
        //判断是否时正确的电话号码，正确时才进行发送验证码操作
        if (r.matcher(tel).matches()){
            //定义静态变量 互亿无线发送短信的接口地址，把它存入一个静态常量类，可以给后续重复使用
            String Url= CodeMessage.URL;
            //创建HttpClient对象
            HttpClient client = new HttpClient();
            //创建请求对象
            PostMethod method = new PostMethod(Url);
            //设置编码
            client.getParams().setContentCharset("GBK");
            //设置请求头
            method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");
            //随机生成一个6位数的验证码
            int mobile_code = (int)((Math.random()*9+1)*100000);
            //使用随机的验证码拼接一条短信信息
            String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");
            //调用接口时传入的参数
            NameValuePair[] data = {//提交短信
                    //查看用户名 登录用户中心->验证码通知短信>产品总览->API接口信息->APIID
                    new NameValuePair("account", "C83804047"),
                    //查看密码 登录用户中心->验证码通知短信>产品总览->API接口信息->APIKEY
                    new NameValuePair("password", "67817860c56911faa86f3e580122dbbf"),
                    //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
                    new NameValuePair("mobile", tel), //接收短信的手机号码
                    new NameValuePair("content", content),
            };
            method.setRequestBody(data);//将参数添加到POST请求中
            try {
                client.executeMethod(method);//调接口
                String SubmitResult =method.getResponseBodyAsString();//获取响应结果
                Document doc = DocumentHelper.parseText(SubmitResult);
                Element root = doc.getRootElement();
                String code = root.elementText("code");
                String msg = root.elementText("msg");
                String smsid = root.elementText("smsid");
                System.out.println(code);
                System.out.println(msg);
                System.out.println(smsid);
                if("2".equals(code)){
                    System.out.println("短信提交成功");
                    System.out.println("验证码为:"+mobile_code);
                    //把发送的验证码存到Session中
                    request.getSession().setAttribute("code", mobile_code+"");
                    return new Result(true, StatusCode.OK,"验证码发送成功");
                }
            } catch (HttpException e) {
                e.printStackTrace();
                return new Result(false, StatusCode.ERROR,"网络繁忙，请重新再试");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return new Result(false, StatusCode.ERROR,"验证码发送失败，请重新再试");
            } catch (DocumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return new Result(false, StatusCode.ERROR,"验证码发送失败，请重新再试");
            }
        }else{
            return new Result(false, StatusCode.ERROR,"请输入正确的电话号码");
        }
        return null;
    }
}

