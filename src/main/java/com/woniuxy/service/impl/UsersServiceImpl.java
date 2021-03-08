package com.woniuxy.service.impl;

import com.woniuxy.domain.Users;
import com.woniuxy.mapper.UsersMapper;
import com.woniuxy.service.UsersService;
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
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

}
