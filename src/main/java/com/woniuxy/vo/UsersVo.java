package com.woniuxy.vo;

import lombok.Builder;
import lombok.Data;

/*
    用户的vo类
 */
@Data
@Builder
public class UsersVo {

    private String username;
    private String phone;
    private String password;
    private String code;
    private boolean rememberme;
}
