package com.woniuxy.mapper;

import com.woniuxy.domain.MsgInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 消息表 Mapper 接口
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-11
 */
@Mapper
public interface MsginfoMapper extends BaseMapper<MsgInfo> {

    int deleteByPrimaryKey(Integer id);

    int insert(MsgInfo msgInfo);

    int insertSelective(MsgInfo msgInfo);

    MsgInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MsgInfo msgInfo);

    int updateByPrimaryKey(MsgInfo msgInfo);

    List<MsgInfo> select(MsgInfo msgInfo);

    void msgRead(@Param("fromUserId") Integer fromUserId, @Param("toUserId") Integer toUserId);

    List<MsgInfo> selectMsgList(@Param("fromUserId") Integer fromUserId,@Param("toUserId") Integer toUserId);
}
