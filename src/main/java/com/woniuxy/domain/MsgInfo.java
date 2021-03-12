package com.woniuxy.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-11
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @ApiModel(value="MsgInfo对象", description="消息表")
public class MsgInfo implements Serializable {

    private static final long serialVersionUID=1L;

      @ApiModelProperty(value = "消息id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty(value = "消息发送者id")
      private Integer fromUserId;

      @ApiModelProperty(value = "消息发送者名称")
      private String fromUserName;

      @ApiModelProperty(value = "消息接收者id")
      private Integer toUserId;

      @ApiModelProperty(value = "消息接收者名称")
      private String toUserName;

      @ApiModelProperty(value = "消息内容")
      private String content;

      @ApiModelProperty(value = "消息发送时间")
      private Date createTime;

      @ApiModelProperty(value = "是否已读（1 已读）")
      private Integer unReadFlag;


}
