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
import com.woniuxy.Vo.JobCompany;
import com.woniuxy.mapper.JobMapper;
import io.swagger.annotations.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import com.woniuxy.domain.Recruiter;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-06
 */
@RestController
@Api(tags = "职位接口信息")//用于描述接口类的相关信息，作用于类上
@RequestMapping("/job")
@Slf4j

public class JobController {
    @Resource
    JobMapper jobMapper;
    //添加职位
    @PostMapping("addJob")
    @ApiOperation(value = "添加职位",notes = "<span style='color:red;'>添加职位职位的接口</span>")
    //@ApiResponses用于描述响应状态信息
    @ApiResponses({
            @ApiResponse(code = 200,message = "添加成功"),
            @ApiResponse(code=404,message = "没有资源")
    })
    //@ApiImplicitParams用于描述接口参数
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "job",value = "职位对象",dataType = "Job",paramType = "body"),

    })
    public Result addJob( @RequestBody Job job){

        System.out.println("进入了此方法"+job);
        int insert = jobMapper.insert(job);
        if(insert>0){
            return  new Result(true,StatusCode.OK,"新增职位成功","success");
        }else{
            return  new Result(false,StatusCode.UNKNOWNACCOUNT,"新增职位失败",null);
        }
    }
    //查看所有职位
    @GetMapping("getAllJob")
    @ApiOperation(value = "查看所有职位",notes = "<span style='color:red;'>查看所有职位的接口</span>")
    //@ApiResponses用于描述响应状态信息
    @ApiResponses({
            @ApiResponse(code = 200,message = "查看成功"),
            @ApiResponse(code=404,message = "没有资源")
    })
    //@ApiImplicitParams用于描述接口参数
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "recruiterId",value = "招聘者id",dataType = "Integer",paramType = "query",example = "1"),

    })
    public Result getAlljob(int recruiterId){
        System.out.println(recruiterId);
        QueryWrapper<Job> wrapper = new QueryWrapper<>();
        wrapper.eq("recruiterId",recruiterId);
        List<Job> jobs = jobMapper.selectList(wrapper);
        System.out.println(jobs);
        return new Result(true, StatusCode.OK,"查询所有职业成功",jobs);
    }
    //平台方根据职位查找那些公司包含此职位
    @GetMapping("findCompanyJobByJobName")
    @ApiOperation(value = "根据职位查找那些公司包含此职位",notes = "<span style='color:red;'>根据职位查找那些公司包含此职位的接口</span>")
    //@ApiResponses用于描述响应状态信息
    @ApiResponses({
            @ApiResponse(code = 200,message = "查询成功"),
            @ApiResponse(code=404,message = "没有资源")
    })
    //@ApiImplicitParams用于描述接口参数
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "jobName",value = "职位名称",dataType = "String",paramType = "query",example = "java"),

    })
    public Result findCompanyJobByJobName(String jobName){
        System.out.println(jobName);
        List<JobCompany> companyJobByJobName = jobMapper.findCompanyJobByJobName(jobName);
        System.out.println(companyJobByJobName);
        return  new Result(true,StatusCode.OK,"根据输入的职位查询那些公司发布了该职位",companyJobByJobName);
    }
    @GetMapping("deleteJob")
    @ApiOperation(value = "根据职位id删除职位",notes = "<span style='color:red;'>根据职位id删除职位的接口</span>")
    //@ApiResponses用于描述响应状态信息
    @ApiResponses({
            @ApiResponse(code = 200,message = "删除成功"),
            @ApiResponse(code=404,message = "没有资源")
    })
    //@ApiImplicitParams用于描述接口参数
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "jobId",value = "职位id",dataType = "Integer",paramType = "query",example = "1"),
    })
    public Result deleteJob(Integer jobId){
        System.out.println(jobId);
        int i = jobMapper.deleteById(jobId );
        if(i>0){
            return  new Result(true,StatusCode.OK,"删除职位成功");
        }else{
            return  new Result(false,StatusCode.ERROR,"删除职位失败");
        }


    }
    @GetMapping("/searchjobname")
    public Result searchJobNamegy(String sid){//搜索条件职位名称

        int i = Integer.parseInt(sid);
        List<Job> recruiterId = jobMapper.selectList(new QueryWrapper<Job>().eq("recruiterId", i));

        return new Result(false,StatusCode.ERROR,"修改失败",recruiterId);
    }

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
            return new Result(true, StatusCode.OK,"修改成功");
        }
        return new Result(false,StatusCode.ERROR,"修改失败");
    }
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


    //查看发布岗位
    @GetMapping("getJobs")
    public Result getJobs(Recruiter recruiter){
        System.out.println("查看发布岗位");
        List<Job> jobs = jobService.getJobs(recruiter.getId());

        return new Result(true, StatusCode.OK,"查询成功",jobs);
    }

    //跳转发布岗位
    @GetMapping("addToJobs/{id}")
    public Result addToJobs(Integer id){
        System.out.println("增加跳转成功");
        Recruiter recruiter = recruiterService.getById(id);
        return new Result(true,StatusCode.OK,"增加跳转成功",recruiter);
    }

    //发布岗位
//    @PostMapping("addJob")
//    public Result addJobs(@RequestBody Recruiter recruiter, @RequestBody Job job){
//        System.out.println("增加岗位");
//        job.setRecruiterId(recruiter.getId());
//        jobService.addByRid(job);
//        return new Result(true,StatusCode.OK,"新增成功");
//    }

    @GetMapping("updateToJobs")
    public Result updateToJobs(Job job){
        System.out.println("更新跳转");
        Job jobs = jobService.getById(job.getId());
        return new Result(true,StatusCode.OK,"更新跳转成功",jobs);
    }

    @PutMapping("updateJobs")
    public Result updateJobs(@RequestBody Job job){
        System.out.println("更新岗位");
        return new Result();
    }
}

