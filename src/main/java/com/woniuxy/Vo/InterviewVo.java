package com.woniuxy.Vo;

import lombok.Data;

@Data
public class InterviewVo {
    //创建面试的vo
    private int id;
    private int recruiterId;
    private int seekerId;

    private String phone;
    private String companyLocation;
    private long time;
}
