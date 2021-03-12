package com.woniuxy.vo;

import lombok.Builder;
import lombok.Data;

/*
    用户的vo类
 */
@Data
@Builder
public class RecruitersVo {

    private Integer id;
    private String username;
    private String phone;
    private String password;
    private String code;
    private Integer role;
    private boolean rememberme;
    //招聘者职位
    private String myjob;
    //招聘者邮箱
    private String email;
    //招聘者公司id
    private Integer companyId;
}
