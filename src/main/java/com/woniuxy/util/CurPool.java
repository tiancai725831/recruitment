package com.woniuxy.util;

import com.woniuxy.controller.WebSocket;
import com.woniuxy.domain.Users;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 统一管理session、websocket、curUser
 */
public class CurPool {

//    public static CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();
    public static Map<Integer, WebSocket> webSockets = new ConcurrentHashMap<Integer, com.woniuxy.controller.WebSocket>();
    // list 里面第一个存sessionId，第二个存session
    public static Map<Integer, List<Object>> sessionPool = new ConcurrentHashMap<>();
    // 当前登录用户x
    public static Map<String, Users> curUserPool = new ConcurrentHashMap<>();
}
