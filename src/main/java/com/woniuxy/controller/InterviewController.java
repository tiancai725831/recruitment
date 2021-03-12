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
import com.woniuxy.Vo.UsernameDate;
import com.woniuxy.Vo.UsernameTime;
import com.woniuxy.domain.Interview;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.InterviewMapper;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.woniuxy.Vo.Deliver;
import com.woniuxy.Vo.InterviewVo;
import com.woniuxy.domain.Interview;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.CompanyMapper;
import com.woniuxy.mapper.InterviewMapper;
import com.woniuxy.mapper.JobresumeMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static java.lang.Integer.parseInt;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
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
@Slf4j
@Api(tags = "面试操作")
public class InterviewController {
    @Resource
    private InterviewMapper interviewMapper;

    @Resource
    private JobresumeMapper jobresumeMapper;

    @GetMapping("/showinterviemgy")
    public Result addMeeting(String sid){//查询同意面试的人才
        int i = Integer.parseInt(sid);
        List<Deliver> delivers = interviewMapper.selectInterviemGy(i);
        return new Result(true, StatusCode.OK,"查询成功",delivers);
    }



    @PostMapping("/inviteinew")//创建面试表
    public Result insertincite(@RequestBody String id){
        System.out.println();
        String iid = id.substring(0,id.length() - 1);
        int i = Integer.parseInt(iid);
        System.out.println(i);
        InterviewVo interviewVo = interviewMapper.selectInterviewVoByid(i);

        if (!ObjectUtils.isEmpty(interviewVo)) {
            Interview interview = new Interview();
            long l = System.currentTimeMillis();
            interview.setJobId(interviewVo.getId());
            interview.setRecruiterId(interviewVo.getRecruiterId());
            interview.setSeekId(interviewVo.getSeekerId());
            interview.setRecruiterPhone(interviewVo.getPhone());
            interview.setTime(l);
            interview.setPlace(interviewVo.getCompanyLocation());
            System.out.println(interview);
            System.out.println(interviewVo);
            int insert = interviewMapper.insert(interview);
            if(insert>0){
                int i1 = jobresumeMapper.updataisAgeerInvite(i);
                if (i1>0){
                    return new Result(true, StatusCode.OK,"面试邀请成功");
                }
                return new Result(false, StatusCode.ERROR,"面试邀请失败");
            }
        }

        return new Result(false, StatusCode.ERROR,"面试邀请失败");
    }
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

