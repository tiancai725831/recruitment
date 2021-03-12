package com.woniuxy.mapper;

import com.woniuxy.Vo.UsernameTime;
import com.woniuxy.Vo.Deliver;
import com.woniuxy.Vo.InterviewVo;
import com.woniuxy.domain.Interview;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-06
 */
public interface InterviewMapper extends BaseMapper<Interview> {
    //根据seekes的id查询出求职者的姓名以及求职者约定的面试时间
    @Select("SELECT u.username, " +
            "i.time " +
            "from users as u " +
            "join seekers as s  " +
            "on s.userId=u.id " +
            "JOIN interview as i " +
            "on i.seekId=s.id " +
            "WHERE s.id=#{s.id}")
    UsernameTime findUsernameTimeBySeekId(Integer seekId);




    @Select("SELECT  " +
            " js.id, " +
            " jb.jobName, " +
            " us.uname,  " +
            " sk.education, " +
            " sk.jonIntension " +
            "  FROM jobresume AS js  " +
            " JOIN job AS jb " +
            " on js.jobId=jb.id  " +
            " JOIN seekers AS sk  " +
            " on sk.id=js.seekerId  " +
            " JOIN users AS us  " +
            " on sk.id=us.id  " +
            " WHERE js.isAgree=1 "+
                "AND js.recruiterId=#{id}")
    public List<Deliver> selectInterviemGy(int id);//查看同意面试的求职者


    @Select("SELECT \n" +

            "js.recruiterId,\n" +
            "js.seekerId,\n" +
            "jb.id,\n" +
            "us.phone,\n" +
            "cp.companyLocation\n" +
            "\n" +
            "FROM jobresume AS js\n" +
            "JOIN recruiter AS rs\n" +
            "ON js.recruiterId=rs.id\n" +
            "JOIN users AS us\n" +
            "ON us.id=rs.userId\n" +
            "JOIN company AS cp\n" +
            "ON cp.id=rs.companyId\n" +
            "JOIN job AS jb\n" +
            "ON js.jobId=jb.id\n" +
            "WHERE js.id=#{id}")
    public InterviewVo selectInterviewVoByid(int id);//创建面试表需要的数据


}
