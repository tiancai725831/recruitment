package com.woniuxy.mapper;

import com.woniuxy.Vo.JobCompany;
import com.woniuxy.domain.Job;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-06
 */
public interface JobMapper extends BaseMapper<Job> {
    //根据输入的职位名称查询拥有该职位的公司名
    @Select("select j.jobName,c.companyName FROM job AS j JOIN company as c ON j.companyId = c.id where j.jobName = #{jobName}")
    List<JobCompany> findCompanyJobByJobName (String jobName);

}
