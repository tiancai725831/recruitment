package com.woniuxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.woniuxy.Vo.SeekerInfoVo;
import com.woniuxy.domain.Seekers;
import com.woniuxy.domain.Users;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.service.SeekersService;
import com.woniuxy.service.UsersService;
import org.springframework.web.bind.annotation.GetMapping;

import com.woniuxy.domain.Onlineresume;
import com.woniuxy.domain.Projectexperience;
import com.woniuxy.service.OnlineresumeService;
import com.woniuxy.service.ProjectexperienceService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-06
 */
@RestController
@RequestMapping("/seekers")
@Slf4j
@Api(tags = "求职者操作")
public class SeekersController {
    @Resource
    private UsersService usersService;
    @Resource
    private SeekersService seekersService;
    //查询求职者信息
    @GetMapping("getInfo")
    public Result getInfo(Seekers seekers){
        QueryWrapper<Seekers> wrapper = new QueryWrapper<>();
        wrapper.eq("id",seekers.getId());
        Seekers seekerDB = seekersService.getOne(wrapper);
        QueryWrapper<Users> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("id",seekerDB.getUserId());
        Users userDB = usersService.getOne(wrapper1);
        SeekerInfoVo seekerInfoVo = new SeekerInfoVo();
        seekerInfoVo.setSid(seekerDB.getId());
        seekerInfoVo.setUid(userDB.getId());
        seekerInfoVo.setAdvantage(seekerDB.getAdvantage());
        seekerInfoVo.setBirthday(seekerDB.getBirthday());
        seekerInfoVo.setEducation(seekerDB.getEducation());
        seekerInfoVo.setHead(userDB.getHead());
        seekerInfoVo.setIdentity(seekerDB.getIdentity());
        seekerInfoVo.setJobTransition(seekerDB.getJobTransition());
        seekerInfoVo.setJonIntension(seekerDB.getJobIntension());
        seekerInfoVo.setPhone(userDB.getPhone());
        seekerInfoVo.setUname(userDB.getUname());
        seekerInfoVo.setUsername(userDB.getUsername());
        seekerInfoVo.setWorkHours(seekerDB.getWorkHours());
        return new Result(true, StatusCode.OK,"查询求职者信息成功",seekerInfoVo);
    }
    //修改求职者信息
    @GetMapping("updateInfo")
    public Result updateInfo(Users users,Seekers seekers){
        UpdateWrapper<Users> usersUpdateWrapper = new UpdateWrapper<>();
        usersUpdateWrapper.eq("id",users.getId());
        boolean b1 = usersService.update(users, usersUpdateWrapper);
        UpdateWrapper<Seekers> seekersUpdateWrapper = new UpdateWrapper<>();
        seekersUpdateWrapper.eq("id",seekers.getId());
        boolean b = seekersService.update(seekers, seekersUpdateWrapper);
        if (b&&b1){
            return new Result(true, StatusCode.OK,"修改信息成功");
        }
        return new Result(false,StatusCode.ERROR,"修改信息失败");
    }
    @Resource
    private OnlineresumeService onlineresumeService;
    @Resource
    private ProjectexperienceService projectexperienceService;






    //创建在线简历
    @GetMapping("createResumes")
    public Result createResumes(Onlineresume onlineresume, Projectexperience projectexperience){
        onlineresumeService.save(onlineresume);
        projectexperienceService.save(projectexperience);

        return new Result(true,StatusCode.OK,"创建简历成功");
    }
}

