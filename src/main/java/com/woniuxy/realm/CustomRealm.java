package com.woniuxy.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.woniuxy.domain.Users;
import com.woniuxy.service.UsersService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

public class CustomRealm extends AuthorizingRealm {
    @Resource
    private UsersService usersService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String phone = (String) authenticationToken.getPrincipal();
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        Users udb = usersService.getOne(wrapper);
        if (!ObjectUtils.isEmpty(udb)) {
            return new SimpleAuthenticationInfo(udb, udb.getPassword(), ByteSource.Util.bytes(udb.getSalt()), this.getName());
        }
        return null;
    }
}
