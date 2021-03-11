package com.woniuxy.controller;

import com.woniuxy.config.WebSocketPushHandler;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

@RestController
@RequestMapping("/msg")
public class MsgController {
    //全体发送广播
    @PostMapping("sendMsg")
    public Result sendMsg(String msg){
        System.out.println("全体广播消息["+msg+"]");
        TextMessage textMessage = new TextMessage(msg);
        WebSocketPushHandler.sendMessagesToUsers(textMessage);
        return new Result(true, StatusCode.OK,"全体广播发送成功");
    }
    //向指定用户发送消息
    @PostMapping("sendMsgByUser")
    public Result sendMsgByUser(String msg,String userId){
        System.out.println("向 "+userId+" 发送消息，消息内容为:"+msg);
        TextMessage textMessage = new TextMessage(msg);
        WebSocketPushHandler.sendMessageToUser(userId,textMessage);
        return new Result(true,StatusCode.OK,"向个人发送消息");
    }
}
