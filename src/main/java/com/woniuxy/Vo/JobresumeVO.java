package com.woniuxy.Vo;

import lombok.Data;

@Data
public class JobresumeVO {

    //工作岗位
    private String jobName;
    //人名
    private String uname;
    //面试时间
    private Long time;
    //面试id
    private Integer id;
    //招聘id
    private Integer rId;
    //求职者id
    private Integer sId;
    //面试结果
    private String result;
    //公司名
    private String companyName;
}
