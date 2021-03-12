package com.woniuxy.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Projectexperience对象", description="")
public class Projectexperience implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("companyName")
    private String companyName;

    @TableField("departMent")
    private String departMent;

    @TableField("jobName")
    private String jobName;

    @TableField("workingTime")
    private String workingTime;

    @TableField("workPerformance")
    private String workPerformance;

    @TableField("projectName")
    private String projectName;

    @TableField("projectRole")
    private String projectRole;

    private Integer resumeId;


}
