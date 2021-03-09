package com.woniuxy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.domain.Interview;
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
public interface InterviewService extends IService<Interview> {

    List<Interview> getPage(QueryWrapper<Interview> wrapperI);
}
