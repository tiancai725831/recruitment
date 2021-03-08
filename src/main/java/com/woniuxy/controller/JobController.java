package com.woniuxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.Vo.JobCompany;
import com.woniuxy.domain.Job;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.JobMapper;
import org.springframework.web.bind.annotation.GetMapping;
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
 * @since 2021-03-06
 */
@RestController
@RequestMapping("/job")
public class JobController {
    @Resource
    JobMapper jobMapper;
    //查看所有职位
    @GetMapping("getAllJob")
    public Result getAlljob(int recruitId){
        QueryWrapper<Job> wrapper = new QueryWrapper<>();
        wrapper.eq("recruiterId",recruitId);
        List<Job> jobs = jobMapper.selectList(wrapper);
        return new Result(true, StatusCode.OK,"查询所有职业成功",jobs);
    }
    //平台方根据职位查找那些公司包含此职位
    @GetMapping("findCompanyJobByJobName")
    public Result findCompanyJobByJobName(String jobName){
        System.out.println("进入了此方法");
        System.out.println(jobName);
        List<JobCompany> companyJobByJobName = jobMapper.findCompanyJobByJobName(jobName);
        System.out.println(companyJobByJobName);
        return  new Result(true,StatusCode.OK,"根据输入的职位查询那些公司发布了该职位",companyJobByJobName);


    }
}

