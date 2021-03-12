package com.woniuxy.component;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.woniuxy.mapper.UsersMapper;
import com.woniuxy.util.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

public class JWTRealm extends AuthorizingRealm {
    @Resource
    private UsersMapper userMapper;


    //开启自定义realm对jwttoken的支持
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    //授权

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String token = (String) authenticationToken.getPrincipal();

        DecodedJWT verify = JWTUtil.verify(token);
        String username = verify.getClaim("username").asString();
        System.out.println(username);
        if (!StringUtils.hasLength(username)){
            throw new AuthenticationException("token错误");
        }
        return new SimpleAuthenticationInfo(token,token,this.getName());
    }
}
