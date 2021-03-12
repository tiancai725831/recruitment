package com.woniuxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.woniuxy.Vo.JobMeetingVO;
import com.woniuxy.Vo.JobTopVO;
import com.woniuxy.Vo.JobresumeVO;
import com.woniuxy.domain.Interview;
import com.woniuxy.domain.Job;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.service.JobService;
import com.woniuxy.service.RecruiterService;
import com.woniuxy.service.SeekersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

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
    @Resource
    private SeekersService seekersService;

    private Integer jid;

    //求职者投递记录查看
    @GetMapping("getMeeting/{id}")
    public Result getMeeting(@PathVariable Integer id){
        System.out.println("投递管理");
        QueryWrapper<JobMeetingVO> wrapper = new QueryWrapper<>();
        wrapper.eq("s.id", id);
        List<JobMeetingVO> page = seekersService.getJobPage(wrapper);

        return new Result(true, StatusCode.OK,"打开面试反馈",page);
    }

    //招聘者顶置岗位
    @GetMapping("getJobTop/{id}")
    public Result getJobTop(@PathVariable Integer id){
        System.out.println("查看岗位顶置页面");
        QueryWrapper<JobTopVO> wrapper = new QueryWrapper<>();
        wrapper.eq("r.id",id);
        List<JobTopVO> jobPage = recruiterService.getJobPage(wrapper);
        return new Result(true, StatusCode.OK,"打开面试反馈",jobPage);
    }
    //岗位顶置
    @GetMapping("topJob")
    public Result topJob(Integer id){
        System.out.println("岗位进行顶置");
        QueryWrapper<Job> jobWrapper = new QueryWrapper<>();
        jobWrapper.eq("id",id);
        Job job = jobService.getOne(jobWrapper);
        UpdateWrapper<Job> wrapper = new UpdateWrapper<>();
        wrapper.set("top", UUID.randomUUID().toString())
                .eq("id",id);
        jobService.update(new Job(), wrapper);
        return new Result(true,StatusCode.OK,"面试通过与否");
    }

    //查询薪资
    @GetMapping("selectSalary")
    public Result selectSalary(Job job){
        QueryWrapper<Job> wrapper = new QueryWrapper<>();
        wrapper.eq("id",job.getId())
                .ge("minSalary",job.getMinSalary())
                .le("maxSalary",job.getMaxSalary());
        List<Job> list = jobService.list(wrapper);
        return new Result(true,StatusCode.OK,"查询薪资",list);
    }
}

