package com.woniuxy.controller;


import com.woniuxy.Vo.Showresumevo;
import com.woniuxy.domain.Jobresume;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.JobresumeMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-08
 */
@RestController
@RequestMapping("/jobresume")
@Slf4j
@Api(tags = "职位投递的简历")
public class JobresumeController {
    @Resource
    private JobresumeMapper jobresumeMapper;

/*
同意简历，修改职位简历表isAgree字段0为同意一为拒绝
 */
    @PostMapping("confirmResume")//同意面试请求
    public Result confirmResume(@RequestBody String id){


        String iid = id.substring(0,id.length() - 1);
        int i = Integer.parseInt(iid);

        System.out.println("面试的id同意"+i);
        int i1 = jobresumeMapper.updataisAgeerBYID(i);
        if(i1>0){
            return new Result(true, StatusCode.OK,"邀请面试");
        }

        return new Result(false, StatusCode.ERROR,"修改失败");
    }


    @PostMapping("confirmResumeno")//拒绝面试请求
    public Result confirmResumeNO(@RequestBody String id){

        String iid = id.substring(0,id.length() - 1);
        int i = Integer.parseInt(iid);


        int i1 = jobresumeMapper.updataisAgeerNo(i);

        if(i1>0){
            return new Result(true, StatusCode.OK,"拒绝成功");
        }

        return new Result(false, StatusCode.ERROR,"修改失败");
    }

    @PostMapping("/showresumesvo")//查看简历简历
    public Result showResumesVo(@RequestBody String sid){

        System.out.println("前端传来的id"+sid);
        String iid = sid.substring(0,sid.length() - 1);
        int i = Integer.parseInt(iid);

        Showresumevo showresumevo = jobresumeMapper.selectShowResume(i);
        List<Showresumevo> objects = new ArrayList<>();
        objects.add(showresumevo);

        if (!ObjectUtils.isEmpty(showresumevo)) {
            return new Result(true, StatusCode.OK,"查询成功",objects);
        }
        return new Result(false, StatusCode.ERROR,"查询失败",objects);
    }


}

