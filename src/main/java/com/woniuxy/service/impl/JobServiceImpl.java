package com.woniuxy.service.impl;

import com.woniuxy.domain.Job;
import com.woniuxy.mapper.JobMapper;
import com.woniuxy.service.JobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fx
 * @since 2021-03-08
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

    @Resource
    private JobMapper jobMapper;
    @Override
    public Job getByRid(Integer id) {
        Job job = jobMapper.getByRid(id);
        return job;
    }
    @Override
    public boolean deleteByJid(Integer jid) {
        jobMapper.deleteByJid(jid);
        return true;
    }
    @Override
    public List<Job> getJobs(Integer id) {
        jobMapper.getJobs(id);
        return null;
    }
    @Override
    public boolean addByRid(Job job) {
        jobMapper.addByRid(job);
        return true;
    }


}
