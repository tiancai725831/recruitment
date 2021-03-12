package com.woniuxy.vo;

import lombok.Data;

/*
    登录成功后向前端传递招聘者id和token
 */
@Data
public class RecruitersIdAndToken {

    private Integer recruitersId;
    private String token;
}
