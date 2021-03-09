package com.woniuxy.controller;


import com.woniuxy.domain.Job;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.JobMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
            return new Result(true,StatusCode.OK,"修改成功");
        }

        return new Result(false,StatusCode.ERROR,"修改失败");
    }



}

