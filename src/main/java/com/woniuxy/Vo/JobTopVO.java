package com.woniuxy.Vo;

import lombok.Data;

@Data
public class JobTopVO {
    //岗位id
    private Integer id;
    //工作岗位
    private String jobName;
    //公司名
    private String companyName;
    //最低薪资
    private Double minSalary;
    //最高薪资
    private Double maxSalary;
    //工作场所
    private String workPlace;
    //招聘状态
    private String recruitmentStatus;
    //工作经验
    private String experienceRequirement;
}
