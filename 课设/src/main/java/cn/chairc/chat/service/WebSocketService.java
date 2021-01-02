package cn.chairc.chat.service;

import cn.chairc.chat.model.ResultSet;

public interface WebSocketService {

    ResultSet sendChatAll(String message);
}
