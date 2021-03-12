package com.woniuxy.mapper;

import com.woniuxy.Vo.JobCompany;
import com.woniuxy.domain.Job;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fx
 * @since 2021-03-08
 */
public interface JobMapper extends BaseMapper<Job> {
    //根据输入的职位名称查询拥有该职位的公司名
    @Select("select j.jobName,c.companyName FROM job AS j JOIN company as c ON j.companyId = c.id where j.jobName = #{jobName}")
    List<JobCompany> findCompanyJobByJobName (String jobName);

    @Select("select j.* from job j join recruiter r on j.recruiterId = #{id} ")
    public List<Job> getJobs(Integer id);

    @Insert("insert into job values(#{id},#{recruiterId},#{companyId},#{minSalary}," +
            "#{maxSalary},#{welfare},#{workPlace},#{academicRequirements},#{experienceRequirement}," +
            "#{recruitmentStatus},#{jobName},#{jobReleaseTime},#{updatedTime},#{jobTag},#{viewNumber})")
    public Integer addByRid(@RequestParam Job job);

    @Select("select j.* from job j join recruiter r on j.recruiterId = #{id} ")
    Job getByRid(Integer id);

    @Select("DELETE \n" +
            "from job \n" +
            "where id=#{jid}")
    public Integer deleteByJid(Integer jid);

    @Select("select j.* from job j join recruiter r on j.recruiterId = #{id} ")
    public List<Job> getJobs(Integer id);

    @Insert("insert into job values(#{id},#{recruiterId},#{companyId},#{minSalary}," +
            "#{maxSalary},#{welfare},#{workPlace},#{academicRequirements},#{experienceRequirement}," +
            "#{recruitmentStatus},#{jobName},#{jobReleaseTime},#{updatedTime},#{jobTag},#{viewNumber})")
    public Integer addByRid(@RequestParam Job job);





}
