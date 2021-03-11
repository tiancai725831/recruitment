package com.woniuxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.woniuxy.Vo.JobresumeVO;
import com.woniuxy.domain.Interview;
import com.woniuxy.domain.Seekers;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.service.InterviewService;
import com.woniuxy.service.RecruiterService;
import com.woniuxy.service.SeekersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static java.lang.Integer.parseInt;

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
    @Resource
    private SeekersService seekersService;

    //招聘方显示面试反馈
    @GetMapping("getInterview/{id}")
    public Result getInterview(@PathVariable Integer id){
        System.out.println("尝试打开面试反馈");
        QueryWrapper<JobresumeVO> wrapper = new QueryWrapper<>();
        wrapper.eq("r.id", id);
        List<JobresumeVO> page = recruiterService.getPage(wrapper);
        return new Result(true, StatusCode.OK,"打开面试反馈",page);
    }

    //招聘方面试通过
    @GetMapping("passInterview")
    public Result passInterview(Integer id){
        System.out.println("面试通过判断");
//        Interview interview = new Interview();
        UpdateWrapper<Interview> wrapper = new UpdateWrapper<>();
        wrapper.set("result","面试通过")
                .eq("id",id);
        interviewService.update(new Interview(), wrapper);
        return new Result(true,StatusCode.OK,"面试通过与否");
    }
    //招聘方面试未通过
    @GetMapping("noPassInterview")
    public Result noPassInterview(Integer id){
        System.out.println("面试未通过判断");
        UpdateWrapper<Interview> wrapper = new UpdateWrapper<>();
        wrapper.set("result","面试未通过")
                .eq("id",id);
        interviewService.update(new Interview(), wrapper);
        return new Result(true,StatusCode.OK,"面试通过与否");
    }

    //招聘方面试评价
    @GetMapping("updateComment")
    public Result updateComment(Interview interview){
        System.out.println("进行面试评价");
        Interview interview1 = new Interview();
        UpdateWrapper<Interview> wrapper = new UpdateWrapper<>();
            wrapper.set("recruiterComment",interview.getRecruiterComment())
                .eq("id",interview.getId());
        interviewService.update(interview1,wrapper);
        return new Result(true,StatusCode.OK,"评价修改成功");
    }

    //求职者面试查看
    @GetMapping("selectMeeting/{id}")
    public Result selectMeeting(@PathVariable Integer id){
        System.out.println("面试查看");
        QueryWrapper<JobresumeVO> wrapper = new QueryWrapper<>();
        wrapper.eq("s.id", id);
        List<JobresumeVO> page = recruiterService.getPage(wrapper);
        return new Result(true, StatusCode.OK,"打开面试反馈",page);
    }

    //面试方面试评价
    @GetMapping("getComment")
        public Result getComment(Interview interview){
        System.out.println("查看面试评价");
        QueryWrapper<Interview> wrapper = new QueryWrapper<>();
        wrapper.eq("id",interview.getId());
        Interview one = interviewService.getOne(wrapper);
        return new Result(true,StatusCode.OK,"评价修改成功",one);
    }
}

