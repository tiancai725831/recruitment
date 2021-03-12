package com.woniuxy.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 会话列表
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-11
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @ApiModel(value="SessionList对象", description="会话列表")
public class SessionList implements Serializable {

    private static final long serialVersionUID=1L;

      @ApiModelProperty(value = "id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty(value = "所属用户")
      private Integer userId;

      @ApiModelProperty(value = "到用户")
      private Integer toUserId;

      @ApiModelProperty(value = "会话名称")
      private String listName;

      @ApiModelProperty(value = "未读消息数")
      private Integer unReadCount;


}
