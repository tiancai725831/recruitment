package com.woniuxy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.Vo.JobresumeVO;
import com.woniuxy.domain.Recruiter;
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
public interface RecruiterService extends IService<Recruiter> {

    List<JobresumeVO> getPage(QueryWrapper<JobresumeVO> wrapper);

}
