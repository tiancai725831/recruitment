package com.woniuxy.service;

import com.woniuxy.domain.Job;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-06
 */
public interface JobService extends IService<Job> {

    List<Job> getJobs(Integer id);

    public boolean addByRid(Job job);

    Job getByRid(Integer id);

    public boolean deleteByJid(Integer jid);
}
