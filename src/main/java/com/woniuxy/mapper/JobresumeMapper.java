package com.woniuxy.mapper;

import com.woniuxy.Vo.Deliver;
import com.woniuxy.Vo.ShowLike;
import com.woniuxy.Vo.Showresumevo;
import com.woniuxy.domain.Jobresume;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniuxy.domain.Onlineresume;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-08
 */
public interface JobresumeMapper extends BaseMapper<Jobresume> {

    @Select("SELECT  " +
            "js.id, " +
            "jb.jobName, " +
            "us.uname, " +
            "sk.education " +
            "FROM jobresume AS js " +
            "JOIN job AS jb " +
            "on js.jobId=jb.id " +
            "JOIN seekers AS sk " +
            "on sk.id=js.seekerId " +
            "JOIN users AS us " +
            "on sk.id=us.id " +
            "WHERE js.recruiterId=#{id}")
    public List<Deliver> selectDeliverBYId(int id);


    /*
    根据职位简历中间表id修改isAgree字段，同意或者拒绝面试0为拒绝，1为同意2为邀请面试
     */
    @Update("UPDATE jobresume SET isAgree =1 WHERE id=#{id}")
    public int updataisAgeerBYID(int id);

    @Update("UPDATE jobresume SET isAgree =0 WHERE id=#{id}")
    public int updataisAgeerNo(int id);

    @Update("UPDATE jobresume SET isAgree =2 WHERE id=#{id}")
    public int updataisAgeerInvite(int id);


    @Select("SELECT  " +
            "js.id, " +
            "oi.skillTag, " +
            "oi.createdTime, " +
            "oi.certificate, " +
            "oi.educationalExperience, " +
            "pro.companyName, " +
            "pro.jobName,\n" +
            "pro.projectName,\n" +
            "sk.birthday, " +
            "sk.education, " +
            "sk.identity, " +
            "sk.jonIntension, " +
            "us.uname, " +
            "us.phone " +
            "FROM jobresume AS js  " +
            "JOIN onlineresume AS oi " +
            "on oi.seekId=js.seekerId " +
            "JOIN projectexperience AS pro " +
            "on pro.resumeId=js.seekerId " +
            "JOIN seekers AS sk " +
            "on js.seekerId=sk.id " +
            "JOIN users AS us " +
            "on us.id=sk.userId " +
            "WHERE js.id=#{id}")
    public Showresumevo selectShowResume(int id);//查看简历



    @Select("SELECT  " +
            "js.id, " +
            "oi.skillTag, " +
            "oi.createdTime, " +
            "oi.certificate, " +
            "oi.educationalExperience, " +
            "pro.companyName, " +
            "jb.jobName, " +
            "pro.projectName, " +
            "sk.birthday, " +
            "sk.education, " +
            "sk.identity, " +
            "sk.jonIntension, " +
            "us.uname, " +
            "us.phone " +
            "FROM jobresume AS js  " +
            "JOIN onlineresume AS oi " +
            "on oi.seekId=js.seekerId " +
            "JOIN projectexperience AS pro " +
            "on pro.resumeId=js.seekerId " +
            "JOIN seekers AS sk " +
            "on js.seekerId=sk.id " +
            "JOIN users AS us " +
            "on us.id=sk.userId " +
            "JOIN job AS jb " +
            "ON js.jobId=jb.id " +
            "WHERE js.recruiterId=#{sid} " +
            "and jb.jobName LIKE #{aa}  " +
            "AND oi.educationalExperience LIKE #{bb} ")
    public List<Showresumevo> searchResumeGY(ShowLike showLike);//条件收缩后的简历

    @Select("select * from onlineresume where skillTag like #{skillTag}")
    List<Onlineresume> find(String skillTag);

}
