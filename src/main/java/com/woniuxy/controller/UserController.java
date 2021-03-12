package com.woniuxy.controller;


import com.woniuxy.domain.AjaxResult;
import com.woniuxy.domain.Users;

import com.woniuxy.mapper.UsersMapper;
import com.woniuxy.service.RecruiterService;
import com.woniuxy.service.UsersService;
import com.woniuxy.util.CurPool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-11
 */
@RestController

public class UserController {

    @Resource
    private UsersMapper usersMapper;
    @Resource
    private RecruiterService recruiterService;
    @Resource
    private UsersService usersService;
    // 注销
    @GetMapping("/loginOut")
    public AjaxResult<?> loginOut(@RequestParam String name){
        if (name != null && !"".equals(name)){
            CurPool.curUserPool.remove(name);
            Users users = usersMapper.selectUserByName(name);
            CurPool.sessionPool.remove(users.getId());
            CurPool.webSockets.remove(users.getId());
            System.out.println("【websocket消息】连接断开，总数为:"+CurPool.webSockets.size());
        }
        return AjaxResult.success();
    }
    // 注册用户
    @GetMapping("/register")
    public AjaxResult<?> register(@RequestParam String name) {
        String s = usersMapper.selectByName(name);
        if (s != null || "".equals(s)){
            return AjaxResult.failure("改用户已存在");
        }
        Users user = new Users();
        user.setUname(name);
        usersMapper.insert(user);
        return AjaxResult.success();
    }

    // 登录
    @GetMapping("/login")
    public AjaxResult<?> login(String name){
        if (CurPool.curUserPool.get(name) != null){
            return AjaxResult.failure("该用户已登录");
        }
        Users user = usersMapper.selectUserByName(name);
        if (user == null || user.getId() == null){
            return AjaxResult.failure("该用户不存在");
        }
        CurPool.curUserPool.put(user.getUname(), user);
        return AjaxResult.success(user);
    }


}

