package com.woniuxy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.Vo.JobMeetingVO;
import com.woniuxy.Vo.JobresumeVO;
import com.woniuxy.domain.Seekers;
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
public interface SeekersService extends IService<Seekers> {

    List<JobMeetingVO> getJobPage(QueryWrapper<JobMeetingVO> wrapper);
}
