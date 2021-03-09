package com.woniuxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniuxy.Vo.JobresumeVo;
import com.woniuxy.domain.Interview;
import com.woniuxy.domain.Recruiter;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.service.InterviewService;
import com.woniuxy.service.RecruiterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 *  面试
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-06
 */
@RestController
@RequestMapping("/interview")
public class InterviewController {

    @Resource
    private InterviewService interviewService;
    @Resource
    private RecruiterService recruiterService;

    //显示面试反馈
    @GetMapping("getInterview/{id}")
    public Result getInterview(@PathVariable Integer id){
        System.out.println("尝试打开面试反馈");
        QueryWrapper<Recruiter> wrapperR = new QueryWrapper<>();
        wrapperR.eq("id", id);
        Recruiter recruiter = recruiterService.getOne(wrapperR);
        QueryWrapper<Interview> wrapperI = new QueryWrapper<>();
        wrapperI.eq("recruiterId",recruiter.getId());
        List<Interview> page = interviewService.getPage(wrapperI);

        return new Result(true, StatusCode.OK,"打开面试反馈",page);
    }

    //面试判断
    @PostMapping("passInterview")
    public Result passInterview(@RequestBody Integer id){
        System.out.println("面试通过判断");
//        Interview interview = new Interview();
        UpdateWrapper<Interview> wrapper = new UpdateWrapper<>();
        wrapper.set("result",1)
                .eq("id",id);
        interviewService.update(new Interview(), wrapper);
        return new Result(true,StatusCode.OK,"面试通过与否");
    }

    @PostMapping("noPassInterview")
    public Result noPassInterview(@RequestBody Integer id){
        System.out.println("面试未通过判断");
        UpdateWrapper<Interview> wrapper = new UpdateWrapper<>();
        wrapper.set("result",2)
                .eq("id",id);
        interviewService.update(new Interview(), wrapper);
        return new Result(true,StatusCode.OK,"面试通过与否");
    }

    //面试评价
    @PostMapping("updateComment")
    public Result updateComment(@RequestBody Interview interview){
        System.out.println("进行面试评价");
        UpdateWrapper<Interview> wrapper = new UpdateWrapper<>();
        wrapper.set("seekerComment",interview.getSeekerComment())
                .eq("id",interview.getId());
        interviewService.update(interview,wrapper);
        return new Result(true,StatusCode.OK,"评价修改成功");
    }
}

