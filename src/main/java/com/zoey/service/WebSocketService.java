package com.zoey.service;

import com.zoey.util.SnowFlake;
import com.zoey.websocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WebSocketService {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketService.class);

    @Resource
    WebSocketServer webSocketServer;

    @Resource
    private SnowFlake snowFlake;

    @Async
    public void sendInfo(String message, String LOG_ID) {
        // 日志流水号继承vote service

        MDC.put("LOG_ID", LOG_ID);
        webSocketServer.sendInfo(message);
        LOG.info("推送消息了吗？{}",message);
    }
}
