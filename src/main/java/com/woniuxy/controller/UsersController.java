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

import javax.annotation.Resource;
import java.util.List;

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
@Api(tags = "用户接口信息")//用于描述接口类的相关信息，作用于类上
@RequestMapping("/users")
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
    //@ApiResponses用于描述响应状态信息
    @ApiResponses({
            @ApiResponse(code = 200,message = "查询成功"),
            @ApiResponse(code=404,message = "没有资源")
    })
    //@ApiImplicitParams用于描述接口参数
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求

    @Resource
    private UsersService usersService;

    @GetMapping("getUserByName")
    public Result getUserByName(Users users){
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.like("uname",users.getUname());
        List<Users> list = usersService.list(wrapper);
        return new Result(true,StatusCode.OK,"搜索找人",list);
    }
    })
    public Result appealsUnlocked(){
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("locked",1);
        wrapper.eq("isComplainted",1);
        List<Users> users = usersMapper.selectList(wrapper);
        return  new Result(true, StatusCode.OK,"查询所有提出申述的账户信息成功",users);
    }
   //根据传递的用户id进行账户解锁
    @GetMapping("releaseLock")
    @ApiOperation(value = "根据传递的用户id进行账户解锁",notes = "<span style='color:red;'>根据传递的用户id进行账户解锁</span>")
    //@ApiResponses用于描述响应状态信息
    @ApiResponses({
            @ApiResponse(code = 200,message = "解锁成功"),
            @ApiResponse(code=404,message = "没有资源")
    })
    //@ApiImplicitParams用于描述接口参数
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
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
}

