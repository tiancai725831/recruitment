package com.woniuxy.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("jobresume")
public class JobResume {
    private Integer id;
    private Integer recruiterId;
    private Integer jobId;
    private Integer isAgree;
    private Integer seekId;
}
