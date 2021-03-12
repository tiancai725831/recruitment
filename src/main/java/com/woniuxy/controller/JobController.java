package com.woniuxy.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.Vo.JobCompany;
import com.woniuxy.Vo.JobVo;
import com.woniuxy.domain.Job;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.JobMapper;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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
@Api(tags = "职位接口信息")//用于描述接口类的相关信息，作用于类上
@RequestMapping("/job")
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
}

