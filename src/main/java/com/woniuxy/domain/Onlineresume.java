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
@ApiModel(value="Onlineresume对象", description="")
public class Onlineresume implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("seekId")
    private Integer seekId;

    @TableField("skillTag")
    private String skillTag;

    @TableField("updatedTime")
    private Date updatedTime;

    @TableField("createdTime")
    private Date createdTime;

    private String certificate;

    @TableField("projectExperience")
    private String projectExperience;

    @TableField("educationalExperience")
    private String educationalExperience;


}
