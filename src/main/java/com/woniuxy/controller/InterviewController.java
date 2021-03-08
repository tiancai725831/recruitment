package com.woniuxy.controller;


import com.woniuxy.domain.Interview;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.InterviewMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping("/interview")
public class InterviewController {
    @Resource
    InterviewMapper interviewMapper;
    //查看当前之后的面试
    @GetMapping("getNextMeeting")
    public Result getNextMeeting(){
        List<Interview> interviews = interviewMapper.selectList(null);
        Date date = new Date();
        long time = date.getTime();
        List<Interview>listInterviews = new ArrayList<>();
        for(Interview interviewList:interviews){
            if(interviewList.getTime()>time)
                listInterviews.add(interviewList);
        }


        return  new Result(true, StatusCode.OK,"查询当前之后的面试成功",listInterviews);

    }
    //查看当前之前的面试
    @GetMapping("getBeforeMeeting")
    public Result getBeforeMeeting(){
        List<Interview> interviews = interviewMapper.selectList(null);
        Date date = new Date();
        long time = date.getTime();
        List<Interview>listInterviews = new ArrayList<>();
        for(Interview interviewList:interviews){
            if(interviewList.getTime()<time)
                listInterviews.add(interviewList);
        }


        return  new Result(true, StatusCode.OK,"查询当前之前的面试成功",listInterviews);

    }

}

