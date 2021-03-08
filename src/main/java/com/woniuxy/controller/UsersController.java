package com.woniuxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.domain.Users;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.UsersMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
    //查询所有被锁住的账户
    @Resource
    UsersMapper usersMapper;
    @GetMapping("showLockedCounts")
    public Result showLockedCounts(){
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("locked",1);
        List<Users> users = usersMapper.selectList(wrapper);
        System.out.println(users);
        return  new Result(true, StatusCode.OK,"查询所有被锁定的账户信息成功",users);
    }
    //查询所有被锁住并且点了申述解锁的账户
    @GetMapping("showWantedUnlockedCounts")
    public Result showWantedUnlockedCounts(){
        System.out.println("进入了此方法");
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("locked",1);
        wrapper.eq("isComplainted",1);
        List<Users> users = usersMapper.selectList(wrapper);
        System.out.println(users);
        return  new Result(true, StatusCode.OK,"查询所有提出申述的账户信息成功",users);


    }
}

