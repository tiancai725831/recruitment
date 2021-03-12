package com.woniuxy.controller;


import com.woniuxy.domain.AjaxResult;
import com.woniuxy.domain.MsgInfo;
import com.woniuxy.domain.SessionList;
import com.woniuxy.mapper.MsginfoMapper;
import com.woniuxy.mapper.SessionlistMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 消息表 前端控制器
 * </p>
 *
 * @author zhangshuai
 * @since 2021-03-11
 */
@RestController
public class MsginfoController {
    @Resource
    private MsginfoMapper msginfoMapper;

    @Resource
    private SessionlistMapper sessionlistMapper;

    // 消息列表
    @GetMapping("/msgList")
    public AjaxResult<?> msgList(@RequestParam Integer sessionId){
        SessionList sessionList = sessionlistMapper.selectByPrimaryKey(sessionId);
        if(sessionList == null){
            return AjaxResult.success();
        }
        Integer fromUserId = sessionList.getUserId();
        Integer toUserId = sessionList.getToUserId();
        List<MsgInfo> msgInfoList = msginfoMapper.selectMsgList(fromUserId,toUserId);
        // 更新消息已读
        msginfoMapper.msgRead(fromUserId, toUserId);
        // 更新会话里面的未读消息
        sessionlistMapper.delUnReadCount(fromUserId, toUserId);
        return AjaxResult.success(msgInfoList);
    }
}


