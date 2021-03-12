package com.woniuxy.service.impl;

import com.woniuxy.domain.Company;
import com.woniuxy.mapper.CompanyMapper;
import com.woniuxy.service.CompanyService;
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
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

}
