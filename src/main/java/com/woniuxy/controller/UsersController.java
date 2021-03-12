package com.woniuxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.domain.Users;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.service.UsersService;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.domain.Users;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.UsersMapper;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.domain.Recruiter;
import com.woniuxy.domain.Users;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.jwt.JwtUtil;
import com.woniuxy.mapper.RecruiterMapper;
import com.woniuxy.mapper.UsersMapper;
import com.woniuxy.util.CodeMessage;
import com.woniuxy.util.SaltUtils;
import com.woniuxy.vo.RecruitersIdAndToken;
import com.woniuxy.vo.RecruitersVo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import javax.annotation.Resource;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
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
@Api(tags = "用户接口信息")//用于描述接口类的相关信息，作用于类上
@RequestMapping("/users")
@Slf4j

public class UsersController {
    //查询所有被锁住的账户
    @Resource
    UsersMapper usersMapper;
    @GetMapping("showLockedCounts")
    @ApiOperation(value = "查询所有被锁住的账户",notes = "<span style='color:red;'>查询所有被锁住的账户的接口</span>")
    //@ApiResponses用于描述响应状态信息
    @ApiResponses({
            @ApiResponse(code = 200,message = "查询成功"),
            @ApiResponse(code=404,message = "没有资源")
    })
    //@ApiImplicitParams用于描述接口参数
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求

    })
    public Result showLockedCounts(){
        System.out.println("查询所有被锁账户");
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("locked",1);
        List<Users> users = usersMapper.selectList(wrapper);
        System.out.println(users);
        return  new Result(true, StatusCode.OK,"查询所有被锁定的账户信息成功",users);
    }
    //查询申述解锁的账号
    @GetMapping("appealsUnlocked")
    @ApiOperation(value = "查询所有申述解锁的账户",notes = "<span style='color:red;'>查询所有申述解锁的账户</span>")
    @ApiResponses({
            @ApiResponse(code = 200,message = "查询成功"),
            @ApiResponse(code=404,message = "没有资源")
    })
    @ApiImplicitParams({
    })
    public Result appealsUnlocked(){
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("locked",1);
        wrapper.eq("isComplainted",1);
        List<Users> users = usersMapper.selectList(wrapper);
        return  new Result(true, StatusCode.OK,"查询所有提出申述的账户信息成功",users);
    }
    @Resource
    private UsersService usersService;

    @GetMapping("getUserByName")
    public Result getUserByName(Users users){
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.like("uname",users.getUname());
        List<Users> list = usersService.list(wrapper);
        return new Result(true,StatusCode.OK,"搜索找人",list);
    }

   //根据传递的用户id进行账户解锁
    @GetMapping("releaseLock")
    @ApiOperation(value = "根据传递的用户id进行账户解锁",notes = "<span style='color:red;'>根据传递的用户id进行账户解锁</span>")

    @ApiResponses({
            @ApiResponse(code = 200,message = "解锁成功"),
            @ApiResponse(code=404,message = "没有资源")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "countId",value = "账号Id",dataType = "Integer",paramType = "query",example = "1"),
    })
    public Result releaseLock(Integer countId){
        //先通过传递过来的账户id进行查询
        System.out.println("进入了申述解锁");
        System.out.println(countId);
        Users users = usersMapper.selectById(countId);
        users.setLocked(0);
        users.setIsComplainted(0);
        int i = usersMapper.updateById(users);
        if(i>0){
            return  new Result(true, StatusCode.OK,"账户解锁成功");
        }else{
            return  new Result(false, StatusCode.ERROR,"账户解锁失败");
        }


    }


    private HttpSession session;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RecruiterMapper recruiterMapper;

    //发送验证码方法
    @GetMapping("sendVerificationCode")
    @ApiOperation(value = "发送验证码接口，会先判断号码是否已被注册")
    @ApiResponses({
            @ApiResponse(code = 20001,message = "号码已被注册或格式不正确"),
            @ApiResponse(code=20000,message = "号码可以使用，并成功发送验证码")
    })
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "phone",value = "注册电话",dataType = "String",paramType = "path",example = "17777777777"),
    })
    public Result sendVerificationCode(String phone,HttpServletRequest request){
    //        System.out.println("进入发送验证码方法");
    //        System.out.println("获得的电话"+phone);
            String tel=phone;
            //表达式判断电话号码是否正确
            String judge="^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$";
            Pattern r = Pattern.compile(judge);
            //判断是否时正确的电话号码，正确时才进行发送验证码操作
            if (r.matcher(tel).matches()){
            //判断该用户是否已注册
            //先从redis中查询有没有这个电话
            Set<String> redisPhones = redisTemplate.opsForSet().members("phones");
            if(ObjectUtils.isEmpty(redisPhones)){
            //redis中没有就根据前端传来的电话从数据库中查询所有电话
            List<Users> phones = usersMapper.selectList(new QueryWrapper<Users>().eq("phone", null));
    //                System.out.println(phones);
            //数据库结果不为空
            if (!ObjectUtils.isEmpty(phones)){
            //如果数据库中查出来的所有用户为空把电话放到redis中，说明还没有人注册
            //如果从数据库中查询出来的部位空，就说明有数据，就把数据存到redis中
            for (Users users : phones) {
            //循环遍历所有用户对象，把电话一一存进redis中
            redisTemplate.opsForSet().add("phones",users.getPhone());
            }
            }
            }else{
            //这里说明redis缓存中有数据
            //循环遍历数据和输入的电话一一比对
            for (String redisPhone : redisPhones) {
            if(redisPhone.equals(tel)){
    //                        System.out.println("走redis查询方法");
            return new Result(false,StatusCode.ERROR,"该用户已注册");
            }
            }
            }
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
    //                System.out.println(code);
    //                System.out.println(msg);
    //                System.out.println(smsid);
            if("2".equals(code)){
    //                    System.out.println("短信提交成功");
    //                    System.out.println("验证码为:"+mobile_code);
            //把发送的验证码存到Session中
            session = request.getSession();
            session.setAttribute("code", mobile_code+"");
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

    //注册方法
    @PostMapping("register")
    @ApiOperation(value = "注册接口，注册判断")
    @ApiResponses({
            @ApiResponse(code = 20001,message = "电话号码或密码格式不正确"),
            @ApiResponse(code=20000,message = "注册成功")
    })
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "recruitersVo",value = "注册方法vo类，包含用户名、电话、密码、验证码的信息",dataType = "RecruitersVo",paramType = "path",example = "用户名、电话、密码、验证码"),
    })
    public Result register(@RequestBody RecruitersVo recruitersVo){
    //        System.out.println("注册时获得的账号密码和验证码"+ recruitersVo);
            //获取之前发送的验证码
            String code=(String) session.getAttribute("code");
            //表达式判断电话号码是否正确
            String judge="^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$";
            Pattern r = Pattern.compile(judge);
            //判断是否时正确的电话号码，正确时才进行发送验证码操作
            if (r.matcher(recruitersVo.getPhone()).matches()){
            //判断是否时正确的密码格式，正确时才进行发送验证码操作
            if(!recruitersVo.getPassword().equals("")&& recruitersVo.getPassword().length()>=8){
            //判断是否时正确的验证码，正确时才进行发送验证码操作
            if(recruitersVo.getCode().equals(code)){
            String salt = SaltUtils.getSalt(8);
            //创建md5加密工具
            Md5Hash md5Hash = new Md5Hash
            (recruitersVo.getPassword(),salt , 1024);
            String password = md5Hash.toHex();
            //存进数据库,用户名、电话、密码、盐和创建时间
            Users users = new Users();
            users.setUsername(recruitersVo.getUsername());
            users.setPhone(recruitersVo.getPhone());
            users.setPassword(password);
            users.setSalt(salt);
            users.setCreateTime(new Date(System.currentTimeMillis()));
            users.setRole(recruitersVo.getRole());
            int insert = usersMapper.insert(users);
            //如果是招聘方，同时把userid存到seekes表中做关联
            if (users.getRole()==1){
            Recruiter recruiter = new Recruiter();
            recruiter.setUserId(users.getId());
            recruiterMapper.insert(recruiter);
            }
            if (insert>0){
            //把电话放到redis中，会把注册的所有电话都放到redis中
            redisTemplate.opsForSet().add("phones", recruitersVo.getPhone());
            return new Result(true,StatusCode.OK,"注册成功");
            }
            return new Result(false,StatusCode.ERROR,"注册失败");
            }else{
            return new Result(false,StatusCode.ERROR,"验证码错误");
            }
            }else{
            return new Result(false,StatusCode.ERROR,"请按格式输入密码");
            }
            }else{
            return new Result(false, StatusCode.ERROR,"请输入正确的电话号码");
            }
            }

    //登录方法
    @PostMapping("login")
    @ApiOperation(value = "登录接口，登录判断")
    @ApiResponses({
            @ApiResponse(code = 20001,message = "电话号码或密码不正确"),
            @ApiResponse(code=20000,message = "登录成功")
    })
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "recruitersVo",value = "登录方法vo类，包含用户名、电话、密码、验证码的信息",dataType = "RecruitersVo",paramType = "path",example = "用户名、电话、密码、验证码"),
    })
    public Result login(@RequestBody RecruitersVo recruitersVo,HttpServletRequest request){
    //        System.out.println("登录获得的前端参数"+ recruitersVo);
            //通过电话从数据库中查询
            Users users = usersMapper.selectOne
            (new QueryWrapper<Users>().eq("phone", recruitersVo.getPhone()));
            if(ObjectUtils.isEmpty(users)){
            //为空就表示这个账号没注册
            return new Result(false,StatusCode.ERROR,"账号或密码错误");
            }else{
            //创建一个加密工具
            Md5Hash md5Hash = new Md5Hash(recruitersVo.getPassword(), users.getSalt(), 1024);
            //把前端接受的密码直接加密，加密后再和数据库中的加密密码对比
            String s = md5Hash.toHex();
            if(s.equals(users.getPassword())){
            //创建jwt并返回
            HashMap<String,String> map = new HashMap<>();
            map.put("phone",users.getPhone());
            //生成token
            String token = JwtUtil.createToken(map);
            //相同就表示账号密码都匹配，登录成功
            //获得招聘方的id并传到前端
            Integer recruiterId = recruiterMapper.getRecruiterIdByUserId(users.getId());
    //                System.out.println("登录根据电话数据库中查询到的信息"+users);
            //并把userId存到后端缓存中，供后端使用
            redisTemplate.opsForValue().set("loginuser",users.getId());
            RecruitersIdAndToken recruitersIdAndToken = new RecruitersIdAndToken();
            recruitersIdAndToken.setRecruitersId(recruiterId);
            recruitersIdAndToken.setToken(token);
            return new Result(true,StatusCode.OK,"登录成功",recruitersIdAndToken);
            }else{
            //密码不相同
            return new Result(false,StatusCode.ERROR,"账号或密码错误");
            }
            }
            }

    //首页一打开就根据用户id查询登录用户的详情
    @PostMapping("getLoginUser")
    @ApiOperation(value = "获取用户详情")
    @ApiResponses({
            @ApiResponse(code=20000,message = "访问信息成功")
    })
    public Result getLoginUser(){
        String userId = (String)redisTemplate.opsForValue().get("loginuser");
//        String userId = id.substring(0,id.length()-1);
//        System.out.println("getLoginUser查询用户详情时传来的用户id"+userId);
        //一来先从缓存中查询看是否有这个id，有就直接从缓存中获得详情
        if (ObjectUtils.isEmpty(redisTemplate.opsForHash().get("users:id:" + userId, "id"))){
        //如果没有就从数据库中查询
        Users users = usersMapper.selectById(Integer.parseInt(userId));
//            System.out.println("从数据库中查询到的user详情"+users);
        //把查询到的用户信息存到缓存中
        redisTemplate.opsForHash().put("users:id:"+userId,"id",users.getId().toString());
        redisTemplate.opsForHash().put("users:id:"+userId,"username",users.getUsername());
        redisTemplate.opsForHash().put("users:id:"+userId,"phone",users.getPhone());
        return new Result(true,StatusCode.OK,"数据库中成功获得用户信息",users);
        }
        String id1 =(String) redisTemplate.opsForHash().get("users:id:" + userId, "id");
//        System.out.println("首页从缓存中获得的登录用户id："+id1);
//        String s = id1.toString();
        //不为空就把数据存到一个users对象，传到前端
        Users users = new Users();
        users.setId(Integer.valueOf(userId));
        users.setUsername((String) redisTemplate.opsForHash().get("users:id:" + userId, "username"));
        users.setPhone((String) redisTemplate.opsForHash().get("users:id:" + userId, "phone"));
        redisTemplate.opsForHash().get("users:id:" + userId, "id");
        return new Result(true,StatusCode.OK,"缓存中成功获得用户信息",users);
    }
}

