package com.woniuxy.Vo;

import lombok.Data;

@Data
public class JobMeetingVO {
    //工作岗位
    private String jobName;
    //人名
    private String uname;
    //招聘id
    private Integer rId;
    //求职者id
    private Integer sId;
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
