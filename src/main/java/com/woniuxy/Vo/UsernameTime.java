package com.woniuxy.Vo;

import lombok.Data;

@Data
public class UsernameTime {
    //根据求职者id查询出求职者的姓名以及求职者约定的面试时间保存在次Vo中
    private String username;
    Long time;
}
