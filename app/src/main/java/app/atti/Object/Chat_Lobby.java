package app.atti.Object;

public class Chat_Lobby {
    String sender_name;
    String recent_chat;
    String time;
    String sender_email;

    public Chat_Lobby(String sender_name, String recent_chat, String time, String sender_email) {
        this.sender_name = sender_name;
        this.recent_chat = recent_chat;
        this.time = time;
        this.sender_email = sender_email;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getRecent_chat() {
        return recent_chat;
    }

    public void setRecent_chat(String recent_chat) {
        this.recent_chat = recent_chat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSender_email() {
        return sender_email;
    }

    public void setSender_email(String sender_email) {
        this.sender_email = sender_email;
    }
}
