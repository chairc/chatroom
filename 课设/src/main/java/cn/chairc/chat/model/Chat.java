package cn.chairc.chat.model;

public class Chat {

    private String chat_private_id;
    private String chat_username = "System";
    private String chat_text;
    private String chat_time;
    private String chat_ip;

    public String getChat_private_id() {
        return chat_private_id;
    }

    public void setChat_private_id(String chat_private_id) {
        this.chat_private_id = chat_private_id;
    }

    public String getChat_username() {
        return chat_username;
    }

    public void setChat_username(String chat_username) {
        this.chat_username = chat_username;
    }

    public String getChat_text() {
        return chat_text;
    }

    public void setChat_text(String chat_text) {
        this.chat_text = chat_text;
    }

    public String getChat_time() {
        return chat_time;
    }

    public void setChat_time(String chat_time) {
        this.chat_time = chat_time;
    }

    public String getChat_ip() {
        return chat_ip;
    }

    public void setChat_ip(String chat_ip) {
        this.chat_ip = chat_ip;
    }
}
