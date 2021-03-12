package com.woniuxy.controller;



import com.woniuxy.domain.Job;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.JobMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.domain.Job;
import com.woniuxy.domain.Recruiter;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.JobMapper;
import com.woniuxy.service.JobService;
import com.woniuxy.service.RecruiterService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

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
@Slf4j
@Api(tags = "职位操作")
public class JobController {
    @Resource
    private JobMapper jobMapper;








    @GetMapping("/searchjobname")
    public Result searchJobNamegy(String sid){//搜索条件职位名称

        int i = Integer.parseInt(sid);
        List<Job> recruiterId = jobMapper.selectList(new QueryWrapper<Job>().eq("recruiterId", i));

        return new Result(false,StatusCode.ERROR,"修改失败",recruiterId);
    }






    /*
    根据职位前端传回的职位job的id
    修改状态setRecruitmentStatus为1，
    0为开始招聘，1为停止招聘，
    停止招聘
     */
    @PostMapping("stopjob")
    public Result stopJob(Job job){
        int id=job.getId();
        job.setRecruitmentStatus("1");
        int i = jobMapper.updateById(job);
        if (i>0){
            return new Result(true, StatusCode.OK,"修改成功");
        }

        return new Result(false,StatusCode.ERROR,"修改失败");
    }




    @Resource
    private JobService jobService;
    @Resource
    private RecruiterService recruiterService;

    //查看发布岗位
    @GetMapping("getJobs")
    public Result getJobs(Recruiter recruiter){
        System.out.println("查看发布岗位");
        List<Job> jobs = jobService.getJobs(recruiter.getId());

        return new Result(true, StatusCode.OK,"查询成功",jobs);
    }

    //跳转发布岗位
    @GetMapping("addToJobs/{id}")
    public Result addToJobs(Integer id){
        System.out.println("增加跳转成功");
        Recruiter recruiter = recruiterService.getById(id);
        return new Result(true,StatusCode.OK,"增加跳转成功",recruiter);
    }

    //发布岗位
    @PostMapping("addJob")
    public Result addJobs(@RequestBody Recruiter recruiter, @RequestBody Job job){
        System.out.println("增加岗位");
        job.setRecruiterId(recruiter.getId());
        jobService.addByRid(job);
        return new Result(true,StatusCode.OK,"新增成功");
    }

    @GetMapping("updateToJobs")
    public Result updateToJobs(Job job){
        System.out.println("更新跳转");
        Job jobs = jobService.getById(job.getId());
        return new Result(true,StatusCode.OK,"更新跳转成功",jobs);
    }

    @PutMapping("updateJobs")
    public Result updateJobs(@RequestBody Job job){
        System.out.println("更新岗位");
        return new Result();
    }
}

