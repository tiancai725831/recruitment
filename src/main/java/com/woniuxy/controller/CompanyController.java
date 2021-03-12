package com.woniuxy.controller;


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
@RequestMapping("/company")
@Api(tags = "公司的接口信息")
public class CompanyController {

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
        System.out.println("查询公司详情信息获得的参数："+idg);
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
        System.out.println("修改公司详情信息获得的参数："+company);
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

