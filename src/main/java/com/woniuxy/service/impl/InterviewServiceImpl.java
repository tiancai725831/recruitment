package com.woniuxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniuxy.domain.Interview;
import com.woniuxy.mapper.InterviewMapper;
import com.woniuxy.service.InterviewService;
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
public class InterviewServiceImpl extends ServiceImpl<InterviewMapper, Interview> implements InterviewService {

    @Resource
    private InterviewMapper interviewMapper;

    @Override
    public List<Interview> getPage(QueryWrapper<Interview> wrapperI) {
        Page<Interview> page = new Page<>(1,5);
        IPage<Interview> interviewIPage = interviewMapper.selectPage(page, wrapperI);

        return page.getRecords();
    }
}
