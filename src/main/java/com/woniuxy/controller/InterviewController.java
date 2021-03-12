package com.woniuxy.controller;


import com.woniuxy.Vo.UsernameDate;
import com.woniuxy.Vo.UsernameTime;
import com.woniuxy.domain.Interview;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.InterviewMapper;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
@Api(tags = "面试接口信息")//用于描述接口类的相关信息，作用于类上
@RequestMapping("/interview")
public class InterviewController {
    @Resource
    InterviewMapper interviewMapper;
    //查看当前时间之后的面试
    @GetMapping("getNextMeeting")
    @ApiOperation(value = "查询出当前时间之后的面试",notes = "<span style='color:red;'>查询出当前时间之后的面试的接口</span>")
    //@ApiResponses用于描述响应状态信息
    @ApiResponses({
            @ApiResponse(code = 200,message = "查看成功"),
            @ApiResponse(code=404,message = "没有资源")
    })
    //@ApiImplicitParams用于描述接口参数
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求

    })
    public Result getNextMeeting(){
        //查出面试表中的所有数据
        List<Interview> interviews = interviewMapper.selectList(null);
        Date date = new Date();
        //获取当前时间换算为毫秒值
        long time = date.getTime();
        List<Interview>listInterviews = new ArrayList<>();
        //遍历查询出来的面试表中的所有数据中的time(time也是毫秒值)，当面试表中的毫秒数大时，说明是当前时间之后的面试
        for(Interview interviewList:interviews){
            if(interviewList.getTime()>time)
                //申明一个集合，把当前时间之后的面试保存在该集合中
                listInterviews.add(interviewList);
        }
        //再声明一个集合（数据类型为String类型的用户姓名以及Long类型的时间）
        ArrayList<UsernameTime> usernameTimes = new ArrayList<>();
        //遍历所有当前时间之后的面试，把每条数据的面试表id查询出来后，根据此id查询出面试的用户姓名，保存到该集合中
        for(Interview listInterview:listInterviews){
            UsernameTime usernameTimeBySeekId = interviewMapper.findUsernameTimeBySeekId(listInterview.getSeekId());
            usernameTimes.add(usernameTimeBySeekId);
        }
        //因为前段需要看到的不是毫秒值，所以要把上面集合当中的毫秒值换算为时间，返回给前段,再次申明一个集合保存
        ArrayList<UsernameDate> usernameDatess = new ArrayList<>();
        for(UsernameTime usernameTime:usernameTimes){
            UsernameDate usernameDate = new UsernameDate();
            usernameDate.setUsername(usernameTime.getUsername());
            date.setTime(usernameTime.getTime());
            String format = new SimpleDateFormat().format(date);
            usernameDate.setDate(format);
            usernameDatess.add(usernameDate);
        }
        return  new Result(true, StatusCode.OK,"查询当前之后的面试成功",usernameDatess);

    }
    //查看当前之前的面试
    @GetMapping("getBeforeMeeting")
    @ApiOperation(value = "查询出当前时间之前的面试",notes = "<span style='color:red;'>查询出当前时间之前的面试的接口</span>")
    //@ApiResponses用于描述响应状态信息
    @ApiResponses({
            @ApiResponse(code = 200,message = "查看成功"),
            @ApiResponse(code=404,message = "没有资源")
    })
    //@ApiImplicitParams用于描述接口参数
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求

    })
    public Result getBeforeMeeting(){
        System.out.println("进入了此方法");
        List<Interview> interviews = interviewMapper.selectList(null);
        Date date = new Date();
        long time = date.getTime();
        List<Interview>listInterviews = new ArrayList<>();
        for(Interview interviewList:interviews){
            if(interviewList.getTime()<time)
                listInterviews.add(interviewList);
        }
        ArrayList<UsernameTime> usernameTimes = new ArrayList<>();
        for(Interview listInterview:listInterviews){
            UsernameTime usernameTimeBySeekId = interviewMapper.findUsernameTimeBySeekId(listInterview.getSeekId());
            usernameTimes.add(usernameTimeBySeekId);
        }
        ArrayList<UsernameDate> usernameDates = new ArrayList<>();
        for(UsernameTime usernameTime:usernameTimes){
            UsernameDate usernameDate = new UsernameDate();
            usernameDate.setUsername(usernameTime.getUsername());
            date.setTime(usernameTime.getTime());
            String format = new SimpleDateFormat().format(date);
            usernameDate.setDate(format);
            usernameDates.add(usernameDate);
        }

        return  new Result(true, StatusCode.OK,"查询当前之前的面试成功",usernameDates);

    }

}

