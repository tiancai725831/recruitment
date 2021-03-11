package com.woniuxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniuxy.Vo.JobMeetingVO;
import com.woniuxy.Vo.JobresumeVO;
import com.woniuxy.domain.Seekers;
import com.woniuxy.mapper.SeekersMapper;
import com.woniuxy.service.SeekersService;
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
 * @since 2021-03-06
 */
@Service
public class SeekersServiceImpl extends ServiceImpl<SeekersMapper, Seekers> implements SeekersService {

    @Resource
    private SeekersMapper seekersMapper;

    @Override
    public List<JobMeetingVO> getJobPage(QueryWrapper<JobMeetingVO> wrapper) {
        Page<JobMeetingVO> jobMeetingVOPagePage = new Page<>(1,10);
        Page<JobMeetingVO> page = seekersMapper.getJobPage(jobMeetingVOPagePage, wrapper);
        return page.getRecords();
    }
}
