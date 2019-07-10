package app.atti.Object;

public class Chat_Lobby {
    String sender_name;
    String sender_email;
    String chatName;

    public Chat_Lobby() {
    }

    public Chat_Lobby(String sender_name, String sender_email, String chatName) {
        this.sender_name = sender_name;
        this.sender_email = sender_email;
        this.chatName = chatName;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_email() {
        return sender_email;
    }

    public void setSender_email(String sender_email) {
        this.sender_email = sender_email;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }
}
