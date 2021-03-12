package com.woniuxy.mapper;

import com.woniuxy.Vo.JobNameCompanyName;
import com.woniuxy.domain.Company;
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
public interface CompanyMapper extends BaseMapper<Company> {
    //查询出所有公司的所有职位
    @Select("select j.jobName,c.companyName FROM job as j JOIN company as c on c.id=j.companyId")
    List<JobNameCompanyName> findAllCompaniesAndJobs();

}
