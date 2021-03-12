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
@ApiModel(value="Company对象", description="")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @TableField("companyIntroduction")
    @ApiModelProperty(value = "公司简介")
    private String companyIntroduction;

    @TableField("businessInformation")
    @ApiModelProperty(value = "工商信息")
    private String businessInformation;

    @TableField("companyLocation")
    @ApiModelProperty(value = "公司地址")
    private String companyLocation;

    @TableField("companyIndustry")
    @ApiModelProperty(value = "公司行业")
    private String companyIndustry;

    @TableField("StaffNumber")
    @ApiModelProperty(value = "人员规模")
    private Integer StaffNumber;

    @TableField("financing")
    @ApiModelProperty(value = "融资情况")
    private String financing;

    @TableField("companyWebsite")
    @ApiModelProperty(value = "官网")
    private String companyWebsite;

    @TableField("companyEnvironment")
    @ApiModelProperty(value = "公司环境")
    private String companyEnvironment;

    @TableField("recruiterId")
    @ApiModelProperty(value = "招聘者ID")
    private Integer recruiterId;

    @TableField("companyName")
    private String companyName;


}
