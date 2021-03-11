package com.woniuxy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniuxy.Vo.JobresumeVO;
import com.woniuxy.domain.Recruiter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
