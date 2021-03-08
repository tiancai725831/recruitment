package com.woniuxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.domain.Job;
import com.woniuxy.domain.Recruiter;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.service.JobService;
import com.woniuxy.service.RecruiterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 *  职位
 *  查看简历控制层
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-06
 */
@RestController
@RequestMapping("/job")
public class JobController {

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
    public Result addJobs(@RequestBody Recruiter recruiter,@RequestBody Job job){
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

