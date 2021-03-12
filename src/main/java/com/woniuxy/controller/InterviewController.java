package com.woniuxy.controller;


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




}

