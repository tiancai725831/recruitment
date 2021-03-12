package com.woniuxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.domain.Users;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.service.UsersService;
import org.springframework.web.bind.annotation.*;

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

    @Resource
    private UsersService usersService;

    @GetMapping("getUserByName")
    public Result getUserByName(Users users){
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.like("uname",users.getUname());
        List<Users> list = usersService.list(wrapper);
        return new Result(true,StatusCode.OK,"搜索找人",list);
    }
}

