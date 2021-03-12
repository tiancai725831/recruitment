package com.woniuxy.mapper;

import com.woniuxy.domain.Job;
import com.woniuxy.domain.Jobcollection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.commons.collections.ListUtils;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-08
 */
public interface JobcollectionMapper extends BaseMapper<Jobcollection> {
    @Select("select jo.* from jobcollection j join job jo on j.jobId=jo.id where j.seekId=#{id}")
    List<Job> getJobsBySeekerId(int id);
}
