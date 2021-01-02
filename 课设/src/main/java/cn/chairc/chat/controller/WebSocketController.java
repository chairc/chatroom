package cn.chairc.chat.controller;

import cn.chairc.chat.model.ResultSet;
import cn.chairc.chat.service.UserService;
import cn.chairc.chat.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/websocket")
public class WebSocketController {

    @Autowired
    private UserService userService;

    @Autowired
    private WebSocketService webSocketService;

    /**
     * 群发消息
     *
     * @param message
     * @return
     */

    @RequestMapping("/sendAll")
    @ResponseBody
    public ResultSet sendAllMessage(@RequestParam(required = true,value = "chatText") String message) {
        return webSocketService.sendChatAll(message);
    }

    /**
     * 指定会话ID发消息
     *
     * @param message   消息内容
     * @param privateId 连接会话私有ID
     * @return
     */

    /*@RequestMapping(value = "/sendOne", method = RequestMethod.GET)
    @ResponseBody
    public String sendOneMessage(@RequestParam(required = true) String message,
                                 @RequestParam(required = true) String privateId) {
        try {
            WebSocketServer.SendMessage(message, privateId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }*/
}
