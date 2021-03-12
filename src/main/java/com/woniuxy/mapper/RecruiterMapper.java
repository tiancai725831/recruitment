package com.woniuxy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniuxy.Vo.JobMeetingVO;
import com.woniuxy.Vo.JobTopVO;
import com.woniuxy.Vo.JobresumeVO;
import com.woniuxy.domain.Recruiter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.woniuxy.vo.RecruitersVoUC;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-06
 */
public interface RecruiterMapper extends BaseMapper<Recruiter> {
    @Select("SELECT i.*,u.uname,j.jobName\n" +
            "FROM recruiter r\n" +
            "JOIN interview i\n" +
            "ON r.id=i.recruiterId\n" +
            "JOIN job j\n" +
            "ON i.jobId=j.id\n" +
            "JOIN seekers s\n" +
            "ON i.seekId=s.id\n" +
            "JOIN users u\n" +
            "ON s.userId=u.id\n" +
            "${ew.customSqlSegment}")
    Page<JobresumeVO> getPage(Page<JobresumeVO> jobresumeVoPage, @Param(Constants.WRAPPER) QueryWrapper<JobresumeVO> wrapper);

    @Select("SELECT j.*,c.*\n" +
            "FROM recruiter r\n" +
            "JOIN job j\n" +
            "ON j.recruiterId=r.id\n" +
            "JOIN company c\n" +
            "ON c.id=j.companyId\n" +
            "${ew.customSqlSegment}\n" +
            "ORDER BY j.updatedTime DESC\n")
    Page<JobTopVO> getJobPage(Page<JobTopVO> jobMeetingVOPage, @Param(Constants.WRAPPER) QueryWrapper<JobTopVO> wrapper);
    //查招聘方的用户名、真实姓名、注册电话、职位、邮箱、公司
//    @Select("SELECT " +
//            " u.username, " +
//            " u.phone, " +
//            " u.uname, " +
//            " u.head, " +
//            " r.email " +
//            "FROM recruiter AS r " +
//            "JOIN users AS u " +
//            "ON r.userId=u.id "+
//            "WHERE u.id=#{id};")
    @Select("SELECT  " +
            " u.username, " +
            " u.phone, " +
            " u.uname, " +
            " u.head, " +
            " r.myJob, " +
            " r.email, " +
            " c.companyIntroduction " +
            "FROM recruiter AS r " +
            "JOIN users AS u " +
            "ON r.userId=u.id " +
            "JOIN company AS c " +
            "ON c.recruiterId=r.id " +
            "WHERE u.id=#{id}")
    RecruitersVoUC getRecruiterInfoByUserId(Integer userId);

    //查询用户详情不包括公司信息
    @Select("SELECT  " +
            " u.username, " +
            " u.phone, " +
            " u.uname, " +
            " u.head, " +
            " r.myJob, " +
            " r.email " +
            "FROM recruiter AS r " +
            "JOIN users AS u " +
            "ON r.userId=u.id " +
            "WHERE u.id=#{id}")
    RecruitersVoUC getRecruiterInfoByUserIdNotCompany(Integer userId);

    //保存招聘方修改的个人信息
    @Update("UPDATE " +
            "users AS u, " +
            "recruiter AS r  " +
            "SET u.username=#{username}, " +
            "u.uname=#{uname}, " +
            "r.email=#{email}, " +
            "r.myjob=#{myjob} " +
            "WHERE u.id=#{id}")
    Integer saveUpdateInfoByUserId(RecruitersVoUC recruitersVo);

    //根据登录的userid获得招聘方的id
    @Select("SELECT r.id AS '招聘方id' " +
            "FROM recruiter AS r " +
            "JOIN users AS u " +
            "ON r.userId=u.id " +
            "WHERE u.id=#{id}")
    Integer getRecruiterIdByUserId(Integer userId);
}
