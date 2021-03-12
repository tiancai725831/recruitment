package com.woniuxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniuxy.Vo.JobMeetingVO;
import com.woniuxy.Vo.JobTopVO;
import com.woniuxy.Vo.JobresumeVO;
import com.woniuxy.domain.Recruiter;
import com.woniuxy.mapper.RecruiterMapper;
import com.woniuxy.service.RecruiterService;
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
public class RecruiterServiceImpl extends ServiceImpl<RecruiterMapper, Recruiter> implements RecruiterService {

    @Resource
    private RecruiterMapper recruiterMapper;

    @Override
    public List<JobresumeVO> getPage(QueryWrapper<JobresumeVO> wrapper) {
        Page<JobresumeVO> jobresumeVoPage = new Page<>(1,10);
        Page<JobresumeVO> page = recruiterMapper.getPage(jobresumeVoPage, wrapper);
        return page.getRecords();
    }

    @Override
    public List<JobTopVO> getJobPage(QueryWrapper<JobTopVO> wrapper) {
        Page<JobTopVO> jobMeetingVOPage = new Page<>(1, 10);
        Page<JobTopVO> jobPage = recruiterMapper.getJobPage(jobMeetingVOPage, wrapper);
        return jobPage.getRecords();
    }
}
