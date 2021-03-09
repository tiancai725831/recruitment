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

    private Integer jid;

    //查看发布岗位
    @GetMapping("getJobs/{id}")
    public Result getJobs(@PathVariable Integer id){
        System.out.println("查看发布岗位");
        List<Job> jobs = jobService.getJobs(id);

        return new Result(true, StatusCode.OK,"查询成功",jobs);
    }

    //查看岗位详情
    @GetMapping("viewJob")
    public Result viewJob(@PathVariable Integer id){
        System.out.println("查看岗位详情");
        Job job = jobService.getById(id);
        return new Result(true,StatusCode.OK,"岗位详情",job);
    }

    //跳转发布岗位
    @GetMapping("addToJobs/{id}")
    public Result addToJobs(@PathVariable Integer id){
        System.out.println("增加跳转成功");
        Job job = jobService.getById(id);
        return new Result(true,StatusCode.OK,"增加跳转成功",job);
    }

    //发布岗位
    @PostMapping("addJob")
    public Result addJobs(@RequestBody Recruiter recruiter,@RequestBody Job job){
        System.out.println("增加岗位");
        job.setRecruiterId(recruiter.getId());
        jobService.addByRid(job);
        return new Result(true,StatusCode.OK,"新增成功");
    }

    //更新岗位跳转
    @GetMapping("updateToJobs/{id}")
    public Result updateToJobs(@PathVariable Integer id){
        System.out.println("更新跳转");
        Job job = jobService.getByRid(id);
        return new Result(true,StatusCode.OK,"更新跳转成功",job);
    }

    //更新岗位
    @PutMapping("updateJob")
    public Result updateJob(@RequestBody Job job){
        System.out.println("更新岗位");
        QueryWrapper<Job> wrapper = new QueryWrapper<>();
        wrapper.eq("id",job.getId());
        jobService.update(job, wrapper);
        return new Result(true,StatusCode.OK,"更新成功");
    }

    @DeleteMapping("deleteJob/{id}")
    public Result deleteJob(@PathVariable Integer id){
        QueryWrapper<Job> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        jobService.remove(wrapper);
        return new Result(true,StatusCode.OK,"删除岗位");
    }
}

