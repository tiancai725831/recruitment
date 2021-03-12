package com.woniuxy.mapper;

import com.woniuxy.Vo.UsernameTime;
import com.woniuxy.domain.Interview;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-06
 */
public interface InterviewMapper extends BaseMapper<Interview> {
    //根据seekes的id查询出求职者的姓名以及求职者约定的面试时间
    @Select("SELECT u.username, " +
            "i.time " +
            "from users as u " +
            "join seekers as s  " +
            "on s.userId=u.id " +
            "JOIN interview as i " +
            "on i.seekId=s.id " +
            "WHERE s.id=#{s.id}")
    UsernameTime findUsernameTimeBySeekId(Integer seekId);




}
