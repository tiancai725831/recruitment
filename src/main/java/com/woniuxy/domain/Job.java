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
 * @since 2021-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Job对象", description="")
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @TableField("recruiterId")
    @ApiModelProperty(value = "招聘者ID")
    private Integer recruiterId;

    @TableField("companyId")
    @ApiModelProperty(value = "公司ID")
    private Integer companyId;

    @TableField("minSalary")
    @ApiModelProperty(value = "薪资范围")
    private String minSalary;

    @TableField("maxSalary")

    @ApiModelProperty(value = "最大薪资")

    private String maxSalary;

    @TableField("welfare")
    @ApiModelProperty(value = "福利待遇")
    private String welfare;

    @TableField("workPlace")
    @ApiModelProperty(value = "工作地点")
    private String workPlace;

    @TableField("academicRequirements")
    @ApiModelProperty(value = "学历要求")
    private String academicRequirements;

    @TableField("experienceRequirement")
    @ApiModelProperty(value = "经验要求")
    private String experienceRequirement;

    @TableField("recruitmentStatus")
    @ApiModelProperty(value = "招聘状态")
    private String recruitmentStatus;

    @TableField("jobName")
    @ApiModelProperty(value = "职位需求")
    private String jobName;

    @TableField("jobReleaseTime")
    @ApiModelProperty(value = "发布时间")
    private Date jobReleaseTime;

    @TableField("updatedTime")
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    @TableField("jobTag")
    @ApiModelProperty(value = "职位标签")
    private String jobTag;

    @TableField("viewNumber")
    @ApiModelProperty(value = "查看次数")
    private Integer viewNumber;


}
