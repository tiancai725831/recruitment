package com.woniuxy.controller;


import com.woniuxy.domain.Onlineresume;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.OnlineresumeMapper;
import com.woniuxy.mapper.RecruiterMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/onlineresume")
@Slf4j
@Api(tags = "简历操作")
public class OnlineresumeController {


    @Resource
    private RecruiterMapper recruiterMapper;

    @Resource
    private OnlineresumeMapper onlineresumeMapper;


    /*
    查询所有简历
     */
    @GetMapping("getResumes")
    public Result getResumes(){
        List<Onlineresume> onlineresumes = onlineresumeMapper.selectList(null);
        return new Result(true, StatusCode.OK,"修改成功",onlineresumes);
    }

}

