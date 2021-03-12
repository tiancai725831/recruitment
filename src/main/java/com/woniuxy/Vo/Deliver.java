package com.woniuxy.Vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Deliver implements Serializable {

    //查看投递的简历
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String jonIntension;
    private String jobName;
    private String uname;
    private String education;
}
