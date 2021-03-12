package com.woniuxy.vo;

import lombok.Data;
/*
    根据用户id查询招聘方的用户名、注册电话、职位、邮箱、公司id和公司简介
 */
@Data
public class RecruitersVoUC {
    //这表示用户id，userId
    private Integer id;
    private String username;
    private String phone;
    //真实姓名
    private String uname;
    //头像
    private String head;
    //招聘者职位
    private String myjob;
    //招聘者邮箱
    private String email;
    //招聘者公司id
    private Integer companyId;
    //招聘者公司简介
    private String companyIntroduction;
}
