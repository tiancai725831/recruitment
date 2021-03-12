package com.woniuxy.controller;

import com.woniuxy.domain.MsgInfo;
import com.woniuxy.domain.SessionList;
import com.woniuxy.domain.Users;
import com.woniuxy.mapper.MsginfoMapper;
import com.woniuxy.mapper.SessionlistMapper;
import com.woniuxy.mapper.UsersMapper;

import com.woniuxy.util.CurPool;
import com.woniuxy.util.JsonUtils;
import com.woniuxy.util.SpringContextUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@ServerEndpoint("/websocket/{userId}/{sessionId}")
//此注解相当于设置访问URL
public class WebSocket {

    @Resource
    private SessionlistMapper sessionlistMapper;

    @Resource
    private UsersMapper usersMapper;

    @Resource
    private MsginfoMapper msginfoMapper;

    private Session session;


    @OnOpen
    public void onOpen(Session session,@PathParam(value="userId")Integer userId, @PathParam(value="sessionId")String sessionId) {
        this.session = session;
        CurPool.webSockets.put(userId,this);
        List<Object> list = new ArrayList<>();
        list.add(sessionId);
        list.add(session);
        CurPool.sessionPool.put(userId , list);
        System.out.println("【websocket消息】有新的连接，总数为:"+CurPool.webSockets.size());
    }

    @OnClose
    public void onClose() {
        // 断开连接删除用户删除session
        Integer userId = Integer.parseInt(this.session.getRequestParameterMap().get("userId").get(0));
        CurPool.sessionPool.remove(userId);
        CurPool.webSockets.remove(userId);
        if (usersMapper == null){
            this.usersMapper = (UsersMapper) SpringContextUtil.getBean("userMapper");
        }
        Users users = usersMapper.selectByPrimaryKey(userId);
        CurPool.curUserPool.remove(users.getUname());
        System.out.println("【websocket消息】连接断开，总数为:"+CurPool.webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {

        String sessionId = this.session.getRequestParameterMap().get("sessionId").get(0);
        if (sessionId == null){
            System.out.println("sessionId 错误");
        }
        // 在这里无法注入Mapper所以使用这种方式注入Mapper
        if (sessionlistMapper == null){
            this.sessionlistMapper = (SessionlistMapper)SpringContextUtil.getBean("seesionListMapper");
        }
        if (usersMapper == null){
            this.usersMapper = (UsersMapper)SpringContextUtil.getBean("userMapper");
        }
        if (msginfoMapper == null){
            this.msginfoMapper = (MsginfoMapper)SpringContextUtil.getBean("msgInfoMapper");
        }
        SessionList sessionList = sessionlistMapper.selectByPrimaryKey(Integer.parseInt(sessionId));
        Users users = usersMapper.selectByPrimaryKey(sessionList.getUserId());
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setContent(message);
        msgInfo.setCreateTime(new Date());
        msgInfo.setFromUserId(sessionList.getUserId());
        msgInfo.setFromUserName(users.getUname());
        msgInfo.setToUserId(sessionList.getToUserId());
        msgInfo.setToUserName(sessionList.getListName());
        msgInfo.setUnReadFlag(0);
        // 消息持久化
        msginfoMapper.insert(msgInfo);

        // 判断用户是否存在，不存在就结束
        List<Object> list = CurPool.sessionPool.get(sessionList.getToUserId());
        if (list == null || list.isEmpty()){
            // 用户不存在，更新未读数
            sessionlistMapper.addUnReadCount(sessionList.getToUserId(),sessionList.getUserId());
        }else{
            // 用户存在，判断会话是否存在
            String id = sessionlistMapper.selectIdByUser(sessionList.getToUserId(), sessionList.getUserId())+"";
            String o = list.get(0) + "";
            if (id.equals(o)){
                // 会话存在直接发送消息
                sendTextMessage(sessionList.getToUserId(), JsonUtils.objectToJson(msgInfo));
            }else {
                // 判断会话列表是否存在
                if (id == null || "".equals(id) || "null".equals(id)){
                    // 新增会话列表
                    SessionList tmpSessionList = new SessionList();
                    tmpSessionList.setUserId(sessionList.getToUserId());
                    tmpSessionList.setToUserId(sessionList.getUserId());
                    tmpSessionList.setListName(users.getUname());
                    tmpSessionList.setUnReadCount(1);
                    sessionlistMapper.insert(tmpSessionList);
                }else {
                    // 更新未读消息数量
                    sessionlistMapper.addUnReadCount(sessionList.getToUserId(),sessionList.getUserId());
                }
                // 会话不存在发送列表消息
                List<SessionList> sessionLists = sessionlistMapper.selectByUserId(sessionList.getToUserId());
                sendTextMessage(sessionList.getToUserId() ,JsonUtils.objectToJson(sessionLists));
            }
        }
        System.out.println("【websocket消息】收到客户端消息:"+message);
    }

    // 此为广播消息
//    public void sendAllMessage(String message) {
//        for(WebSocket webSocket : webSockets) {
//            System.out.println("【websocket消息】广播消息:"+message);
//            try {
//                webSocket.session.getAsyncRemote().sendText(message);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    // 此为单点消息 (发送文本)
    public void sendTextMessage(Integer userId, String message) {
        Session session = (Session)CurPool.sessionPool.get(userId).get(1);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息 (发送对象)
//    public void sendObjMessage(String sessionId, Object message) {
//        Session session = CurPool.sessionPool.get(sessionId);
//        if (session != null) {
//            try {
////                session.getAsyncRemote().sendObject(message);
//                session.getBasicRemote().sendText(JsonUtils.objectToJson(message));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

}