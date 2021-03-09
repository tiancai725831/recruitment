package com.woniuxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.woniuxy.Vo.SeekerInfoVo;
import com.woniuxy.domain.Onlineresume;
import com.woniuxy.domain.Projectexperience;
import com.woniuxy.domain.Seekers;
import com.woniuxy.domain.Users;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.service.OnlineresumeService;
import com.woniuxy.service.ProjectexperienceService;
import com.woniuxy.service.SeekersService;
import com.woniuxy.service.UsersService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
public class SeekersController {
    @Resource
    private UsersService usersService;
    @Resource
    private SeekersService seekersService;
    @Resource
    private OnlineresumeService onlineresumeService;
    @Resource
    private ProjectexperienceService projectexperienceService;



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
        seekerInfoVo.setJonIntension(seekerDB.getJonIntension());
        seekerInfoVo.setPhone(userDB.getPhone());
        seekerInfoVo.setUname(userDB.getUname());
        seekerInfoVo.setUsername(userDB.getUsername());
        seekerInfoVo.setWorkHours(seekerDB.getWorkHours());
        return new Result(true, StatusCode.OK,"查询求职者信息成功",seekerInfoVo);
    }
    //修改求职者信息
    @PostMapping("updateInfo")
    public Result updateInfo(@RequestBody SeekerInfoVo seekerInfoVo){
        Seekers seekers = new Seekers();
        seekers.setId(seekerInfoVo.getSid());
        seekers.setAdvantage(seekerInfoVo.getAdvantage());
        seekers.setBirthday(seekerInfoVo.getBirthday());
        seekers.setEducation(seekerInfoVo.getEducation());
        seekers.setIdentity(seekerInfoVo.getIdentity());
        seekers.setJobTransition(seekerInfoVo.getJobTransition());
        seekers.setJonIntension(seekerInfoVo.getJonIntension());
        seekers.setWorkHours(seekerInfoVo.getWorkHours());

        QueryWrapper<Seekers> seekersQueryWrapper = new QueryWrapper<>();
        seekersQueryWrapper.eq("id",seekerInfoVo.getSid());
        Seekers seekerDB = seekersService.getOne(seekersQueryWrapper);

        Users users = new Users();
        users.setUsername(seekerInfoVo.getUsername());
        users.setPhone(seekerInfoVo.getPhone());

        UpdateWrapper<Seekers> seekersUpdateWrapper = new UpdateWrapper<>();
        seekersUpdateWrapper.eq("id",seekers.getId());
        boolean b = seekersService.update(seekers, seekersUpdateWrapper);

        UpdateWrapper<Users> usersUpdateWrapper = new UpdateWrapper<>();
        usersUpdateWrapper.eq("id",seekerDB.getUserId());
        boolean b1 = usersService.update(users, usersUpdateWrapper);

        if(b&&b1){
            return new Result(true,StatusCode.OK,"修改个人信息成功");
        }
        return new Result(false,StatusCode.ERROR,"修改个人信息失败");


    }


    //创建在线简历
    @GetMapping("createResumes")
    public Result createResumes(Onlineresume onlineresume, Projectexperience projectexperience){
        onlineresumeService.save(onlineresume);
        projectexperienceService.save(projectexperience);

        return new Result(true,StatusCode.OK,"创建简历成功");
    }

    @RequestMapping("upload")
    public Result upload(MultipartFile upload, HttpServletRequest request) throws IOException {
        //获取上传文件的真实文件名
        String realName = upload.getOriginalFilename();
        //设置上传文件存放的目录：target/classes/static/files
        String realPath = "";
        System.out.println(realPath);
        //按日期存放上传文件的日期目录
        String dateDir = new SimpleDateFormat("yyyyMMdd").format(new Date());
        //构建上传文件真实存放目录
        File uploadDir = new File(realPath,dateDir);
        if (!uploadDir.exists()) {//目录不存在时，创建
            uploadDir.mkdirs();
        }
        //设置上传文件前缀：时间戳+UUID
        String uploadFileNamePrefix=new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+ UUID.randomUUID().toString().replace("-","");
        //得到上传文件后缀
        String uploadFileNameSuffix = FilenameUtils.getExtension(realName);
        //构建上传到服务器的文件名
        String uploadFileName=uploadFileNamePrefix+"."+uploadFileNameSuffix;
        //将文件上传到服务器
        upload.transferTo(new File(uploadDir,uploadFileName));

        return new Result();
    }
}

