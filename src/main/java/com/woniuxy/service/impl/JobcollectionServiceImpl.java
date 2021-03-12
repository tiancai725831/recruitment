package com.woniuxy.service.impl;

import com.woniuxy.domain.Job;
import com.woniuxy.domain.Jobcollection;
import com.woniuxy.mapper.JobcollectionMapper;
import com.woniuxy.service.JobcollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-08
 */
@Service
public class JobcollectionServiceImpl extends ServiceImpl<JobcollectionMapper, Jobcollection> implements JobcollectionService {
    @Resource
    private JobcollectionMapper jobcollectionMapper;
    @Override
    public List<Job> getJobsBySeekerId(int id) {
        return jobcollectionMapper.getJobsBySeekerId(id);
    }
}
