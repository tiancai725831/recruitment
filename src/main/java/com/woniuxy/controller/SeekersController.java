package com.woniuxy.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.woniuxy.Vo.SeekerInfoVo;
import com.woniuxy.Vo.SeekerInfoVo2;
import com.woniuxy.domain.*;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.service.*;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    @Resource
    private JobcollectionService jobcollectionService;
    @Resource
    private JobService jobService;
    @Resource
    private JobresumeService jobresumeService;


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

    @GetMapping("getInfo2")
    public Result getInfo2(Seekers seekers){
        QueryWrapper<Seekers> wrapper = new QueryWrapper<>();
        wrapper.eq("id",seekers.getId());
        Seekers seekerDB = seekersService.getOne(wrapper);
        QueryWrapper<Users> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("id",seekerDB.getUserId());
        Users userDB = usersService.getOne(wrapper1);
        SeekerInfoVo2 seekerInfoVo = new SeekerInfoVo2();
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

        QueryWrapper<Onlineresume> onlineresumeQueryWrapper = new QueryWrapper<>();
        onlineresumeQueryWrapper.eq("seekId",seekers.getId());
        Onlineresume serviceOne = onlineresumeService.getOne(onlineresumeQueryWrapper);
        if (!ObjectUtils.isEmpty(serviceOne)){
            seekerInfoVo.setProjectExperience(serviceOne.getProjectExperience());
        }
        return new Result(true, StatusCode.OK,"查询求职者信息成功",seekerInfoVo);
    }



    //修改求职者信息    存在问题：json字符串转为对象出错  解决：要用一个string类型的参数接受，再进行对象转换
    @PostMapping(value = "updateInfo",produces = "application/json;utf-8")
    public Result updateInfo(MultipartFile file,String seekerInfoVo) throws IOException {
        //        设置头像图片名称前缀
        String uploadFileNamePrefix=new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+ UUID.randomUUID().toString().replace("-","");
//        设置头像图片名称后缀
        String originalFilename = file.getOriginalFilename();
//        设置头像图片全名
        String filename=uploadFileNamePrefix+originalFilename;
//        头像上传到服务器保存
        file.transferTo(new File("D:/thirdworkspace/img/uploads/"+filename));

        //将json字符串转为对象seekerInfoVo
        SeekerInfoVo seekerInfoVo1 = JSONObject.parseObject(seekerInfoVo, SeekerInfoVo.class);


        Seekers seekers = new Seekers();
        seekers.setId(seekerInfoVo1.getSid());
        seekers.setAdvantage(seekerInfoVo1.getAdvantage());
        System.out.println(seekers.getAdvantage());
        seekers.setBirthday(seekerInfoVo1.getBirthday());
        seekers.setEducation(seekerInfoVo1.getEducation());
        seekers.setIdentity(seekerInfoVo1.getIdentity());
        seekers.setJobTransition(seekerInfoVo1.getJobTransition());
        seekers.setJonIntension(seekerInfoVo1.getJonIntension());
        seekers.setWorkHours(seekerInfoVo1.getWorkHours());


        QueryWrapper<Seekers> seekersQueryWrapper = new QueryWrapper<>();
        seekersQueryWrapper.eq("id",seekerInfoVo1.getSid());
        Seekers seekerDB = seekersService.getOne(seekersQueryWrapper);

        Users users = new Users();
        users.setUsername(seekerInfoVo1.getUsername());
        users.setPhone(seekerInfoVo1.getPhone());
        users.setHead(filename);

        UpdateWrapper<Seekers> seekersUpdateWrapper = new UpdateWrapper<>();
        seekersUpdateWrapper.eq("id",seekers.getId());
        boolean b = seekersService.update(seekers, seekersUpdateWrapper);

        UpdateWrapper<Users> usersUpdateWrapper = new UpdateWrapper<>();
        usersUpdateWrapper.eq("id",seekerDB.getUserId());
        boolean b1 = usersService.update(users, usersUpdateWrapper);

        if(b&&b1){
            return new Result(true,StatusCode.OK,"修改个人信息成功",filename);
        }
        return new Result(false,StatusCode.ERROR,"修改个人信息失败");


    }


    //创建在线简历
    @PostMapping(value = "createResumes",produces = "application/json;utf-8")
    public Result createResumes(String seekerInfoVo,MultipartFile file) throws IOException {
        String uploadFileNamePrefix=new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+ UUID.randomUUID().toString().replace("-","");
//        设置头像图片名称后缀
        String originalFilename = file.getOriginalFilename();
//        设置头像图片全名
        String filename=uploadFileNamePrefix+originalFilename;
//        头像上传到服务器保存
        file.transferTo(new File("D:/thirdworkspace/img/uploads/"+filename));

        SeekerInfoVo2 seekerInfoVo1 = JSONObject.parseObject(seekerInfoVo, SeekerInfoVo2.class);
//先查询是否创建过简历，如果没有才进行创建
        QueryWrapper<Onlineresume> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("seekId",seekerInfoVo1.getSid());
        Onlineresume resumeDB = onlineresumeService.getOne(queryWrapper);
        if(ObjectUtils.isEmpty(resumeDB)){
            Seekers seekers = new Seekers();
            seekers.setId(seekerInfoVo1.getSid());
            seekers.setAdvantage(seekerInfoVo1.getAdvantage());
            seekers.setBirthday(seekerInfoVo1.getBirthday());
            seekers.setEducation(seekerInfoVo1.getEducation());
            seekers.setIdentity(seekerInfoVo1.getIdentity());
            seekers.setJobTransition(seekerInfoVo1.getJobTransition());
            seekers.setJonIntension(seekerInfoVo1.getJonIntension());
            seekers.setWorkHours(seekerInfoVo1.getWorkHours());


            QueryWrapper<Seekers> seekersQueryWrapper = new QueryWrapper<>();
            seekersQueryWrapper.eq("id",seekerInfoVo1.getSid());
            Seekers seekerDB = seekersService.getOne(seekersQueryWrapper);

            Users users = new Users();
            users.setUname(seekerInfoVo1.getUname());
            users.setPhone(seekerInfoVo1.getPhone());


            UpdateWrapper<Seekers> seekersUpdateWrapper = new UpdateWrapper<>();
            seekersUpdateWrapper.eq("id",seekers.getId());
            boolean b = seekersService.update(seekers, seekersUpdateWrapper);

            UpdateWrapper<Users> usersUpdateWrapper = new UpdateWrapper<>();
            usersUpdateWrapper.eq("id",seekerDB.getUserId());
            boolean b1 = usersService.update(users, usersUpdateWrapper);

            Onlineresume onlineresume = new Onlineresume();
            onlineresume.setSeekId(seekerInfoVo1.getSid());
            onlineresume.setProjectExperience(seekerInfoVo1.getProjectExperience());
            onlineresume.setCertificate(filename);
            onlineresumeService.save(onlineresume);


            return new Result(true,StatusCode.OK,"创建简历成功",filename);
        }

        return new Result(false,StatusCode.ERROR,"请勿重复创建");
    }


    //上传附件简历
    @RequestMapping(value = "upResume",produces = "application/json;utf-8")
    public Result upResume(MultipartFile file,String user) throws IOException {
        String uploadFileNamePrefix=new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+ UUID.randomUUID().toString().replace("-","");
//        设置头像图片名称后缀
        String originalFilename = file.getOriginalFilename();
//        设置头像图片全名
        String filename=uploadFileNamePrefix+originalFilename;
//        头像上传到服务器保存
        file.transferTo(new File("D:/thirdworkspace/img/uploads/"+filename));

        Seekers parseObject = JSONObject.parseObject(user, Seekers.class);
        System.out.println(parseObject.getId());
        parseObject.setResume(filename);
//        QueryWrapper<Seekers> updateWrapper = new QueryWrapper<>();
        UpdateWrapper<Seekers> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",parseObject.getId());
        seekersService.update(parseObject,updateWrapper);

        return new Result(true,StatusCode.OK,"上传附件简历成功",filename);

    }
    //打开附件简历
    @RequestMapping("openResume")
    public Result openResume(Integer id){
        QueryWrapper<Seekers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        Seekers seekerDB = seekersService.getOne(queryWrapper);

        return new Result(true,StatusCode.OK,"打开附件简历成功",seekerDB.getResume());
    }


    //收藏职位
    @RequestMapping("collectJob")
    public Result collectJob(@RequestBody Jobcollection jobcollection){
        System.out.println(jobcollection);
        QueryWrapper<Jobcollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("seekId",jobcollection.getSeekId());
        queryWrapper.eq("jobId",jobcollection.getJobId());
        List<Jobcollection> list = jobcollectionService.list(queryWrapper);
        if (!ObjectUtils.isEmpty(list)){
            if (!list.isEmpty()){
                return new Result(false,StatusCode.ERROR,"请勿重复收藏");
            }
        }
        jobcollectionService.save(jobcollection);
        return new Result(true,StatusCode.OK,"收藏职位成功");
    }
    //取消收藏
    @RequestMapping("cancelCollection")
    public Result cancelCollection(@RequestBody Jobcollection jobcollection){
        QueryWrapper<Jobcollection> jobcollectionQueryWrapper = new QueryWrapper<>();
        jobcollectionQueryWrapper.eq("jobId",jobcollection.getJobId());
        jobcollectionQueryWrapper.eq("seekId",jobcollection.getSeekId());
        jobcollectionService.remove(jobcollectionQueryWrapper);
        return new Result(true,StatusCode.OK,"取消收藏成功");
    }

    //查看所有职位
    @RequestMapping("getJob")
    public Result getJob(){
        List<Job> jobList = jobService.list(null);
        return new Result(true,StatusCode.OK,"查询所有职位成功",jobList);
    }

    //投递简历
    @RequestMapping("handleResume")
    public Result handleResume(@RequestBody Jobresume jobresume){
        QueryWrapper<Jobresume> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("jobId",jobresume.getJobId());
        queryWrapper.eq("recruiterId",jobresume.getRecruiterId());
        queryWrapper.eq("seekerId",jobresume.getSeekerId());
        List<Jobresume> list = jobresumeService.list(queryWrapper);
        if (!ObjectUtils.isEmpty(list)){
            if (!list.isEmpty()){

                return new Result(false,StatusCode.ERROR,"勿重复投递");
            }
        }

        jobresumeService.save(jobresume);
        return new Result(true,StatusCode.OK,"投递简历成功");
    }
    //查询收藏的职位
    @RequestMapping("getLikeJob")
    public Result getLikeJob(Integer seekId){
        List<Job> jobs = jobcollectionService.getJobsBySeekerId(seekId);

        return new Result(true,StatusCode.OK,"查询收藏职位成功",jobs);
    }


}

