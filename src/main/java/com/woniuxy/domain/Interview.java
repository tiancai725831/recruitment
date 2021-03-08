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
@ApiModel(value="Interview对象", description="")
public class Interview implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("recruiterId")
    private Integer recruiterId;

    @TableField("recruiterPhone")
    private String recruiterPhone;

    @TableField("seekId")
    private Integer seekId;

    @TableField("jobId")
    private Integer jobId;

    private Long time;

    private String place;

    @TableField("recruiterComment")
    private String recruiterComment;

    @TableField("seekerComment")
    private String seekerComment;

    private Integer result;

    private Date createtime;

    private Date updatetime;


}
