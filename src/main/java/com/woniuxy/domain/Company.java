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
 * @since 2021-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Company对象", description="")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("companyIntroduction")
    private String companyIntroduction;

    @TableField("businessInformation")
    private String businessInformation;

    @TableField("companyLocation")
    private String companyLocation;

    @TableField("companyIndustry")
    private String companyIndustry;

    @TableField("StaffNumber")
    private Integer StaffNumber;

    private String financing;

    @TableField("companyWebsite")
    private String companyWebsite;

    @TableField("companyEnvironment")
    private String companyEnvironment;

    @TableField("recruiterId")
    private Integer recruiterId;


}
