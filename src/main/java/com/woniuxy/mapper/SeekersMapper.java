package com.woniuxy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniuxy.Vo.JobMeetingVO;
import com.woniuxy.Vo.JobresumeVO;
import com.woniuxy.domain.Seekers;
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
public interface SeekersMapper extends BaseMapper<Seekers> {

    @Select("SELECT u.uname,c.companyName,j.*\n" +
            "FROM seekers s\n" +
            "JOIN jobcollection jc\n" +
            "ON jc.seekID=s.id\n" +
            "JOIN job j\n" +
            "ON j.id=jc.jobId\n" +
            "JOIN company c\n" +
            "ON j.companyId=c.id\n" +
            "JOIN users u\n" +
            "ON s.userId=u.id\n" +
            "${ew.customSqlSegment}")
    Page<JobMeetingVO> getJobPage(Page<JobMeetingVO> jobresumePage, @Param(Constants.WRAPPER) QueryWrapper<JobMeetingVO> wrapper);
}
