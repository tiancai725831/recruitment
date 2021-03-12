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
@ApiModel(value="Interview对象", description="")
public class Interview implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Integer id;

    @TableField("recruiterId")
    @ApiModelProperty(value = "招聘者ID")
    private Integer recruiterId;

    @TableField("recruiterPhone")
    @ApiModelProperty(value = "招聘者联系方式")
    private String recruiterPhone;

    @TableField("seekId")
    @ApiModelProperty(value = "求职者ID")
    private Integer seekId;

    @TableField("jobId")
    @ApiModelProperty(value = "职位ID")
    private Integer jobId;

    @TableField("time")
    @ApiModelProperty(value = "时间")
    private Long time;


    @TableField("place")
    @ApiModelProperty(value = "地点")
    private String place;

    @TableField("recruiterComment")
    @ApiModelProperty(value = "招聘者评价")
    private String recruiterComment;

    @TableField("seekerComment")
    @ApiModelProperty(value = "求职者评价")
    private String seekerComment;


    @TableField("result")
    @ApiModelProperty(value = "面试结果")
    private String result;

    @TableField("createtime")
    @ApiModelProperty(value = "创建时间")
    private Date createtime;

    @TableField("updatetime")
    @ApiModelProperty(value = "修改时间")
    private Date updatetime;


}
