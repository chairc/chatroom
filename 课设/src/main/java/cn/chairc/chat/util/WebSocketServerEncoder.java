package cn.chairc.chat.util;

import cn.chairc.chat.model.Chat;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class WebSocketServerEncoder implements Encoder.Text<Chat> {
    @Override
    public String encode(Chat chat) throws EncodeException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(chat);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
