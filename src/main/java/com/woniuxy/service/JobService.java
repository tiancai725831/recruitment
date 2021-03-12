package com.woniuxy.service;

import com.woniuxy.domain.Job;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fx
 * @since 2021-03-08
 */
public interface JobService extends IService<Job> {
    Job getByRid(Integer id);
    public boolean deleteByJid(Integer jid);
    List<Job> getJobs(Integer id);
    public boolean addByRid(Job job);
}
