package com.woniuxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.Vo.JobMeetingVO;
import com.woniuxy.Vo.JobresumeVO;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.service.JobService;
import com.woniuxy.service.RecruiterService;
import com.woniuxy.service.SeekersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
}

