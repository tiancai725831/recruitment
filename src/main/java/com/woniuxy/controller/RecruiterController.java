package com.woniuxy.controller;


import com.woniuxy.domain.Users;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.RecruiterMapper;
import com.woniuxy.mapper.UsersMapper;
import com.woniuxy.vo.RecruitersVo;
import com.woniuxy.vo.RecruitersVoUC;
import io.swagger.annotations.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-06
 *
 * 招聘方控制类
 */
@RestController
@RequestMapping("/recruiter")
@Api(tags = "招聘方的接口信息")
public class RecruiterController {

    @Resource
    private UsersMapper usersMapper;

    private HttpSession session;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RecruiterMapper recruiterMapper;

    //首页根据用户id查询登录用户的详情
    @PostMapping("getLoginUser")
    @ApiOperation(value = "查询登录招聘方详情接口")
    @ApiResponses({
            @ApiResponse(code=20000,message = "查询成功")
    })
    public Result getLoginUser(){
//        String userId = id.substring(0,id.length()-1);
        System.out.println("招聘方获得详情方法");
        String userId = redisTemplate.opsForValue().get("loginuser").toString();
        System.out.println("getLoginUser查询用户详情时传来的用户id"+userId);
        RecruitersVoUC recruiterInfoByUserId = recruiterMapper.getRecruiterInfoByUserId(Integer.parseInt(userId));
        System.out.println(recruiterInfoByUserId);
        //一来先从缓存中查询看是否有这个id，有就直接从缓存中获得详情
//        if (ObjectUtils.isEmpty(redisTemplate.opsForHash().get("RecruiterVo:id:" + userId, "id"))){
//            //如果没有就从数据库中查询
//            Users users = usersMapper.selectById(Integer.parseInt(userId));
//            System.out.println("从数据库中查询到的user详情"+users);
//            //把查询到的用户信息存到缓存中
//            redisTemplate.opsForHash().put("users:id:"+userId,"id",users.getId().toString());
//            redisTemplate.opsForHash().put("users:id:"+userId,"username",users.getUsername());
//            redisTemplate.opsForHash().put("users:id:"+userId,"phone",users.getPhone());
//            return new Result(true, StatusCode.OK,"数据库中成功获得用户信息",users);
//        }
//        String id1 =(String) redisTemplate.opsForHash().get("users:id:" + userId, "id");
//        System.out.println("首页从缓存中获得的登录用户id："+id1);
////        String s = id1.toString();
//        //不为空就把数据存到一个users对象，传到前端
//        Users users = new Users();
//        users.setId(Integer.valueOf(userId));
//        users.setUsername((String) redisTemplate.opsForHash().get("users:id:" + userId, "username"));
//        users.setPhone((String) redisTemplate.opsForHash().get("users:id:" + userId, "phone"));
//        redisTemplate.opsForHash().get("users:id:" + userId, "id");
//        return new Result(true,StatusCode.OK,"缓存中成功获得用户信息",users);
        return new Result(true,StatusCode.OK,"缓存中成功获得用户信息",recruiterInfoByUserId);
    }

    //修改用户详情
    @PostMapping("saveUpdateInfo")
    @ApiOperation(value = "修改招聘方用户详情")
    @ApiResponses({
            @ApiResponse(code = 20001,message = "修改信息失败"),
            @ApiResponse(code=20000,message = "修改信息成功")
    })
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "recruitersVo",value = "修改招聘方详情vo类，包含用户信息",dataType = "RecruitersVoUC",paramType = "path",example = "用户详情"),
    })
    public Result saveUpdateInfoByUserId(@RequestBody RecruitersVoUC recruitersVo){
        String userId = redisTemplate.opsForValue().get("loginuser").toString();
        recruitersVo.setId(Integer.parseInt(userId));
        System.out.println("修改用户详情获得的参数"+recruitersVo);
        Integer integer = recruiterMapper.saveUpdateInfoByUserId(recruitersVo);
        if (integer>0){
            return new Result(true,StatusCode.OK,"修改用户详情成功");
        }
        return new Result(false,StatusCode.OK,"修改用户详情失败");
    }

    //修改密码
    @PostMapping("saveUpdatePassword")
    @ApiOperation(value = "修改招聘方用户登录密码")
    @ApiResponses({
            @ApiResponse(code = 20001,message = "修改密码失败"),
            @ApiResponse(code=20000,message = "修改密码成功")
    })
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "recruitersVo",value = "用户输入的新密码和招聘方id",dataType = "RecruitersVo",paramType = "path",example = "密码、id"),
    })
    public Result updatePassword(@RequestBody RecruitersVo recruitersVo){
        String userId = redisTemplate.opsForValue().get("loginuser").toString();
        recruitersVo.setId(Integer.parseInt(userId));
        System.out.println("修改密码方法获得的参数密码："+recruitersVo.getPassword()+"+id:"+recruitersVo.getId());
        //现根据当前用户id获得当前用户的盐、密码等信息
        Users users = usersMapper.selectById(recruitersVo.getId());
        //创建加密工具
        //根据新密码再加密密码
        Md5Hash md5Hash = new Md5Hash(recruitersVo.getPassword(), users.getSalt(),1024);
        String s = md5Hash.toHex();
        //再把vo对象的密码更改为加密后的密码
        recruitersVo.setPassword(s);
        //调用方法存进数据库
        Integer integer = usersMapper.updatePasswordByUserId(recruitersVo);
        if (integer>0){
            return new Result(true,StatusCode.OK,"修改密码成功");
        }
        return new Result(false,StatusCode.ERROR,"修改密码失败");
    }

    //上传头像，头像单独修改
    @PostMapping("uploadhead")
    @ApiOperation(value = "用户上传头像并修改")
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "file",value = "上传的头像文件",dataType = "MultipartFile",paramType = "path",example = "上传的头像图片"),
    })
    public void upLoadHead(MultipartFile file){
        System.out.println("上传头像获得的参数head"+file);
        //获取上传文件存放在服务器中的路径,并把/替换为\
        String path="D:/java/shiyan/upload".replace("/",File.separator);
        //把路径放入IO流中
        File file1=new File(path);
        //判断这个目录层级是否已存在，不存在就创建一个
        if(!file1.exists()){
            //不存在就创建
            file1.mkdirs();
        }
        //获取上传文件的原始名称
        String upFile=file.getOriginalFilename();
        //从获取得上传文件名称中截取上传文件的格式
        String upEnd=upFile.substring(upFile.lastIndexOf("."));
        //避免上传文件后文件重名，设置UUID
        String uuid= UUID.randomUUID().toString();
        //获取动态系统时间毫秒数
        long l=System.currentTimeMillis();
        //组成上传后文件的名称
        String upFileName=uuid+l+upEnd;
        //执行上传方法,把上传文件存到服务器指定目录下
        try {
            file.transferTo(new File(path,upFileName));
            //成功存入就或取当前已登录的用户的userid，把数据库的路径也改了
            String loginuserid = redisTemplate.opsForValue().get("loginuser").toString();
            //修改head字段
            Users users = new Users();
            users.setId(Integer.parseInt(loginuserid));
            users.setHead(upFileName);
            //根据id修改
            usersMapper.updateById(users);
//            return new Result(true,StatusCode.OK,"上传头像成功")
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

