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
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.CompanyMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.domain.Company;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.CompanyMapper;
import com.woniuxy.mapper.RecruiterMapper;
import com.woniuxy.mapper.UsersMapper;
import io.swagger.annotations.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import javax.annotation.Resource;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
    @Resource
    private UsersMapper usersMapper;

    private HttpSession session;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RecruiterMapper recruiterMapper;

    @Resource
    private CompanyMapper companyMapper;

    //根据关联的招聘者id查询公司信息
    @PostMapping("getCompanyInfo")
    @ApiOperation(value = "根据关联的招聘者id查询公司信息")
    @ApiResponses({
            @ApiResponse(code = 20001,message = "暂无该公司信息，请添加"),
            @ApiResponse(code=20000,message = "查询公司详情信息成功")
    })
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "recruiterId",value = "当前登录的招聘者id",dataType = "String",paramType = "path",example = "1"),
    })
    public Result getCompanyInfo(@RequestBody String recruiterId){
        String idg = recruiterId.substring(0, recruiterId.length() - 1);
//        System.out.println("查询公司详情信息获得的参数："+idg);
        //从数据库查询公司信息
        Company companyInfo = companyMapper.selectOne
                (new QueryWrapper<Company>().
                        eq("recruiterId", Integer.parseInt(idg)));
        if (ObjectUtils.isEmpty(companyInfo)){
            return new Result(false, StatusCode.ERROR,"暂无该公司信息，请添加");
        }
        return new Result(true, StatusCode.OK,"查询公司详情信息成功",companyInfo);
    }

    //添加，更改公司信息
    @PostMapping("updateCompanyInfo")
    @ApiOperation(value = "添加，更改公司信息")
    @ApiResponses({
            @ApiResponse(code = 20001,message = "修改信息失败"),
            @ApiResponse(code=20000,message = "修改信息成功")
    })
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "company",value = "公司详情信息",dataType = "Company",paramType = "path",example = "公司详情信息"),
    })
    public  Result updateCompanyInfo(@RequestBody Company company){
//        System.out.println("修改公司详情信息获得的参数："+company);
        //先根据公司名查询该公司为空的话，就存入一个RecruiterId
        Company companyInfo = companyMapper.selectOne
                (new QueryWrapper<Company>().
                        eq("recruiterId", company.getRecruiterId()));
        if (ObjectUtils.isEmpty(companyInfo)){
            //先存入RecruiterId这个信息把RecruiterId关联起来
            Company company1 = new Company();
            company1.setRecruiterId(company.getRecruiterId());
            companyMapper.insert(company1);
        }
        int update = companyMapper.update
                (company,new QueryWrapper<Company>().
                        eq("recruiterId",company.getRecruiterId()));
        if (update>0){
            return new Result(true,StatusCode.OK,"修改信息成功");
        }
        return new Result(false,StatusCode.ERROR,"修改信息失败");
    }
}

