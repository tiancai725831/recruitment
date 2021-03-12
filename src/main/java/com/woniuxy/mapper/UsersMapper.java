package com.woniuxy.mapper;

import com.woniuxy.domain.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniuxy.vo.RecruitersVo;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fx
 * @since 2021-03-08
 */
public interface UsersMapper extends BaseMapper<Users> {

    //修改密码方法
    @Update("UPDATE " +
            "users " +
            "SET `password`=#{password} " +
            "WHERE " +
            "id=#{id} ")
    Integer updatePasswordByUserId(RecruitersVo recruitersVo);
}
