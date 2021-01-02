package cn.chairc.chat.util;

import cn.chairc.chat.model.Chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/chatOnline/websocket",encoders = {WebSocketServerEncoder.class})
@Component
public class WebSocketServer {

    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class); //slf4j
    private static final AtomicInteger OnlineCount = new AtomicInteger(0);  //当前在线数
    // concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
    private static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();

    /**
     * 初始化
     */

    @PostConstruct
    public void init() {
        log.info("websocket 已加载加载！！！");
    }

    /**
     * 连接建立成功调用的方法
     */

    @OnOpen
    public void onOpen(Session session) {
        Chat chat = new Chat();
        chat.setChat_text("当前" + session + "连接成功");
        SessionSet.add(session);    //在数据集中添加新打开的session
        int cnt = OnlineCount.incrementAndGet();    //当前在线数加1
        log.info("有连接加入，当前连接数为：{}", cnt);
        SendMessage(session, chat);
    }

    /**
     * 连接关闭调用的方法
     */

    @OnClose
    public void onClose(Session session) {
        SessionSet.remove(session);     //在数据集中移除关闭调用的session
        int cnt = OnlineCount.decrementAndGet();    //当前在线数减1
        log.info("有连接关闭，当前连接数为：{}", cnt);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */

    @OnMessage
    public void onMessage(String message, Session session) {
        Chat chat = new Chat();
        chat.setChat_text("收到消息，消息内容：" + message);
        log.info("来自客户端的消息：{}", message);
        SendMessage(session, chat);
    }

    /**
     * 出现错误
     *
     * @param session
     * @param error
     */

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：{}，Session ID： {}", error.getMessage(), session.getId());
        error.printStackTrace();
    }

    /**
     * 发送消息
     *
     * @param session
     * @param chat
     */

    private static void SendMessage(Session session, Chat chat) {
        try {
            //session.getBasicRemote().sendText(String.format("%s (来自服务器，Session ID=%s)", chat.getChat_text(), session.getId()));
            //ObjectMapper objectMapper = new ObjectMapper();
            //session.getBasicRemote().sendText(objectMapper.writeValueAsString(chat));
            session.getBasicRemote().sendObject(chat);//需要解码器
        } catch (IOException | EncodeException e) {
            log.error("发送消息出错：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 群发消息（单对多聊天）
     *
     * @param chat
     * @throws IOException
     */

    public static void BroadCastInfo(Chat chat) throws IOException {
        for (Session session : SessionSet) {
            if (session.isOpen()) {
                SendMessage(session, chat);
            }
        }
    }

    /**
     * 指定Session发送消息（单对单聊天）
     *
     * @param sessionId
     * @param chat
     * @throws IOException
     */

    public static void SendMessage(Chat chat, String sessionId) throws IOException {
        Session session = null;
        for (Session s : SessionSet) {
            if (s.getId().equals(sessionId)) {
                session = s;
                break;
            }
        }
        if (session != null) {
            SendMessage(session, chat);
        } else {
            log.warn("没有找到你指定ID的会话：{}", sessionId);
        }
    }

}
