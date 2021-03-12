package com.woniuxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.Vo.JobNameCompanyName;
import com.woniuxy.domain.Company;
import com.woniuxy.domain.Job;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.CompanyMapper;
import com.woniuxy.mapper.JobMapper;
import io.swagger.annotations.*;
import org.apache.commons.collections.ListUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@Api(tags = "公司接口信息")//用于描述接口类的相关信息，作用于类上
@RequestMapping("/company")
public class CompanyController {
    @Resource
    CompanyMapper companyMapper;
    @Resource
    JobMapper jobMapper;
    //查询出所有公司的所有职位
    @GetMapping("getAllCompaniesAndJobs")
    @ApiOperation(value = "查询出所有公司的所有职位",notes = "<span style='color:red;'>用来查询出所有公司的所有职位的接口</span>")
    //@ApiResponses用于描述响应状态信息
    @ApiResponses({
            @ApiResponse(code = 200,message = "查询成功"),
            @ApiResponse(code=404,message = "没有资源")
    })
    //@ApiImplicitParams用于描述接口参数
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求

    })
    public Result getAllCompaniesAndJobs(){
        List<JobNameCompanyName> allCompaniesAndJobs = companyMapper.findAllCompaniesAndJobs();
        return new Result(true, StatusCode.OK,"查询所有公司的所有职位成功",allCompaniesAndJobs);
    }
    //查询出所有公司
    @GetMapping("getAllCompanies")
    @ApiOperation(value = "查询出所有公司",notes = "<span style='color:red;'>用来查询出所有公司的接口</span>")
    //@ApiResponses用于描述响应状态信息
    @ApiResponses({
            @ApiResponse(code = 200,message = "查询成功"),
            @ApiResponse(code=404,message = "没有资源")
    })
    //@ApiImplicitParams用于描述接口参数
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求

    })
    public Result getAllCompanies(){
        List<Company> companies = companyMapper.selectList(null);
        return new Result(true, StatusCode.OK,"查询所有公司的所有职位成功",companies);
    }
    //根据前段传递的id值删除该公司
    @GetMapping("deleteCompaniesByCompanyId")
    @ApiOperation(value = "根据id删除公司",notes = "<span style='color:red;'>用前段传递过来的id值删除对应的公司</span>")
    //@ApiResponses用于描述响应状态信息
    @ApiResponses({
            @ApiResponse(code = 200,message = "删除成功"),
            @ApiResponse(code=404,message = "没有资源")
    })
    //@ApiImplicitParams用于描述接口参数
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "companyId",value = "公司Id",dataType = "Integer",paramType = "query",example = "1"),
    })
    public Result deleteCompaniesByCompanyId(Integer companyId){
        System.out.println(companyId);
        QueryWrapper<Job> jobQueryWrapper = new QueryWrapper<Job>();
        jobQueryWrapper.eq("companyId",companyId);
        int delete = jobMapper.delete(jobQueryWrapper);
        if(delete>0){
            return new Result(true,StatusCode.OK,"删除公司成功");
        }else{
            return new Result(false,StatusCode.ERROR,"删除公司失败");
        }


    }
}

