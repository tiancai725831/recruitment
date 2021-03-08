package com.woniuxy.service.impl;

import com.woniuxy.domain.Job;
import com.woniuxy.mapper.JobMapper;
import com.woniuxy.service.JobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
