package com.woniuxy.controller;


import com.woniuxy.domain.AjaxResult;
import com.woniuxy.domain.SessionList;
import com.woniuxy.domain.Users;
import com.woniuxy.mapper.SessionlistMapper;
import com.woniuxy.mapper.UsersMapper;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 会话列表 前端控制器
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-11
 */
@RestController
public class SessionlistController {
    @Resource
    private UsersMapper usersMapper;

    @Resource
    private SessionlistMapper sessionlistMapper;

    /**
     * 已建立会话
     */
    @GetMapping("/sessionList/already")
    public AjaxResult<?> sessionListAlready(@RequestParam Integer id){
        List<SessionList> sessionLists = sessionlistMapper.selectByUserId(id);
        return  AjaxResult.success(sessionLists);
    }

    // 可建立会话的用户
    @GetMapping("/sessionList/not")
    public AjaxResult<?> sessionListNot(@RequestParam Integer id){
        List<Integer> list = sessionlistMapper.selectUserIdByUserId(id);
        list.add(id);
        List<Users> cloudList = usersMapper.getCloudList(list);
        return AjaxResult.success(cloudList);
    }

    // 创建会话
    @GetMapping("/createSession")
    public AjaxResult<?> createSession(@RequestParam Integer id,@RequestParam Integer toUserId,@RequestParam String toUserName){
        SessionList sessionList = new SessionList();
        sessionList.setUserId(id);
        sessionList.setUnReadCount(0);
        sessionList.setListName(toUserName);
        sessionList.setToUserId(toUserId);
        sessionlistMapper.insert(sessionList);
        // 判断对方和我建立会话没有？ 没有也要建立
        Integer integer = sessionlistMapper.selectIdByUser(toUserId, id);
        if (integer == null || integer <= 0){
            Users users = usersMapper.selectByPrimaryKey(id);
            sessionList.setUserId(toUserId);
            sessionList.setToUserId(id);
            sessionList.setListName(users.getUname());
            sessionlistMapper.insert(sessionList);
        }
        return AjaxResult.success();
    }

    // 删除会话
    @GetMapping("/delSession")
    public AjaxResult<?> delSession(@RequestParam Integer sessionId){
        sessionlistMapper.deleteByPrimaryKey(sessionId);
        return AjaxResult.success();
    }

}

