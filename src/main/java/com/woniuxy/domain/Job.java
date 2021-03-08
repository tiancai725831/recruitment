package com.woniuxy.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
 * @since 2021-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Job对象", description="")
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("recruiterId")
    private Integer recruiterId;

    @TableField("companyId")
    private Integer companyId;

    @TableField("minSalary")
    private String minSalary;

    @TableField("maxSalary")
    private String maxSalary;

    private String welfare;

    @TableField("workPlace")
    private String workPlace;

    @TableField("academicRequirements")
    private String academicRequirements;

    @TableField("experienceRequirement")
    private String experienceRequirement;

    @TableField("recruitmentStatus")
    private String recruitmentStatus;

    @TableField("jobName")
    private String jobName;

    @TableField("jobReleaseTime")
    private Date jobReleaseTime;

    @TableField("updatedTime")
    private Date updatedTime;

    @TableField("jobTag")
    private String jobTag;

    @TableField("viewNumber")
    private Integer viewNumber;


}
