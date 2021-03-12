package com.woniuxy.config;


import com.woniuxy.realm.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public Realm realm(){
        CustomRealm customRealm = new CustomRealm();
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1024);
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return customRealm;
    }
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm());
        return defaultWebSecurityManager;
    }
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("/job/findCompanyJobByJobName","anon");
        map.put("/users/showLockedCounts","anon");
        map.put("/users/showWantedUnlockedCounts","anon");
        map.put("/job/addJob","anon");
        map.put("/job/getAllJob","anon");
        map.put("/job/deleteJob","anon");
        map.put("/interview/getNextMeeting","anon");
        map.put("/interview/getBeforeMeeting","anon");
        map.put("/company/getAllCompaniesAndJobs","anon");
        map.put("/company/getAllCompanies","anon");
        map.put("/company/deleteCompaniesByCompanyId","anon");
        map.put("/users/showLockedCounts","anon");
        map.put("/users/appealsUnlocked","anon");
        map.put("/users/releaseLock","anon");
        map.put("/swagger-ui.html","anon");
//        map.put("/**","user");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
}
