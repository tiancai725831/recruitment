package com.woniuxy.mapper;

import com.woniuxy.domain.SessionList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 会话列表 Mapper 接口
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-11
 */
@Mapper
public interface SessionlistMapper extends BaseMapper<SessionList> {
    int deleteByPrimaryKey(Integer id);

    int insert(SessionList sessionList);

    int insertSelective(SessionList sessionList);

    SessionList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SessionList sessionList);

    int updateByPrimaryKey(SessionList sessionList);

    List<Integer> selectUserIdByUserId(Integer id);

    List<SessionList> selectByUserId(Integer id);

    Integer selectIdByUser(@Param("fromId") Integer fromId, @Param("toId") Integer toId);

    SessionList select(SessionList sessionList);

    void addUnReadCount(@Param("userId") Integer userId,@Param("toUserId") Integer toUserId);

    void delUnReadCount(@Param("fromUserId") Integer fromUserId,@Param("toUserId") Integer toUserId);
}
