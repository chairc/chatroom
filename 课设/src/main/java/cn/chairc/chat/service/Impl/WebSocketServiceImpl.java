package cn.chairc.chat.service.Impl;

import cn.chairc.chat.model.Chat;
import cn.chairc.chat.model.ResultSet;
import cn.chairc.chat.service.WebSocketService;
import cn.chairc.chat.util.Tools;
import cn.chairc.chat.util.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WebSocketServiceImpl implements WebSocketService {

    private static Logger log = LoggerFactory.getLogger(WebSocketServiceImpl.class); //slf4j

    /**
     * 群发
     *
     * @param message
     * @return
     */

    @Override
    public ResultSet sendChatAll(String message) {
        ResultSet resultSet = new ResultSet();
        Chat chat = new Chat();
        //设置私有id
        chat.setChat_username(Tools.sessionValidate("username"));
        chat.setChat_text(message);

        if (Tools.sessionValidate("username") == null && Tools.sessionValidate("uid") == null) {
            //未登录
            resultSet.fail("用户未登录");
        } else {
            try {
                WebSocketServer.BroadCastInfo(chat);
                resultSet.success("ok");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*try {
            WebSocketServer.BroadCastInfo(chat);
            resultSet.success("ok");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return resultSet;
    }
}
