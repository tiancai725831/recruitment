package com.woniuxy.mapper;

import com.woniuxy.domain.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.woniuxy.vo.RecruitersVo;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-06
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {
    int deleteByPrimaryKey(Integer id);

    int insert(Users users);

    int insertSelective(Users users);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users users);

    int updateByPrimaryKey(Users users);

    String selectByName(String uname);

    List<Users> getCloudList(@Param("list") List<Integer> list);

    Users selectUserByName(@Param("uname") String uname);
    //修改密码方法
    @Update("UPDATE " +
            "users " +
            "SET `password`=#{password} " +
            "WHERE " +
            "id=#{id} ")
    Integer updatePasswordByUserId(RecruitersVo recruitersVo);
}
