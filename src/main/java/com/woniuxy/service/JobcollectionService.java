package com.woniuxy.service;

import com.woniuxy.domain.Job;
import com.woniuxy.domain.Jobcollection;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-08
 */
public interface JobcollectionService extends IService<Jobcollection> {
    List<Job> getJobsBySeekerId(int id);
}
