package com.woniuxy.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class MyWebSocketInterceptor implements HandshakeInterceptor {

    //在握手之前执行该方法，继续握手返回true，中断握手返回false，通过attributes参数设置WebSocketSession的属性
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("1.用户建议连接。。");
        if (request instanceof ServletServerHttpRequest){
            //request.getParameter("username")方法来获得请求的参数username
            String userId = ((ServletServerHttpRequest) request).getServletRequest().getParameter("userId");
            attributes.put("userId",userId);
            System.out.println("用户唯一标示："+userId);
        }
        return true;
    }

    /**
     * 在握手之后执行该方法. 无论是否握手成功都指明了响应状态码和相应头.
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("2.在握手之后执行该方法. 无论是否握手成功都指明了响应状态码和相应头");
    }
}
