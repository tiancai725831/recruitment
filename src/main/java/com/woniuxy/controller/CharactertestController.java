package com.woniuxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.domain.Charactertest;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.service.CharactertestService;
import org.springframework.web.bind.annotation.GetMapping;
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
 * @since 2021-03-12
 */
@RestController
@RequestMapping("/charactertest")
public class CharactertestController {
    @Resource
    private CharactertestService charactertestService;

    //五维性格测试
    @GetMapping("getCharacter")
    public Result getCharacter(){
        System.out.println("五维性格测试");
        QueryWrapper<Charactertest> wrapper = new QueryWrapper<>();
        wrapper.ge("id",1)
                .le("id",3);
        List<Charactertest> list = charactertestService.list(wrapper);
        return new Result(true, StatusCode.OK,"查询性格",list);
    }
    //进行测试
    @GetMapping("selectCharacter")
    public Result selectCharacter(Integer id){
        System.out.println("测试开始");
        QueryWrapper<Charactertest> wrapper = new QueryWrapper<>();
        wrapper.eq("keyId",id);
        List<Charactertest> list = charactertestService.list(wrapper);
        return new Result(true, StatusCode.OK,"测试展示",list);
    }
}

