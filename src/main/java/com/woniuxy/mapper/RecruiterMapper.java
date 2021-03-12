package com.woniuxy.mapper;

import com.woniuxy.domain.Recruiter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniuxy.vo.RecruitersVoUC;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fx
 * @since 2021-03-08
 */
public interface RecruiterMapper extends BaseMapper<Recruiter> {

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
            "WHERE u.id=1;")
    RecruitersVoUC getRecruiterInfoByUserId(Integer userId);

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
